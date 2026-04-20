package com.example.novacolony

import androidx.compose.ui.platform.LocalContext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.novacolony.ui.theme.NovaColonyTheme

class MainActivity : ComponentActivity() {

    companion object {
        val manager = ColonyManager()
        var simulatorList = mutableStateListOf<CrewMember>()
        var missionCount = 0
        var winCount = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NovaColonyTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    var screen by remember { mutableStateOf("home") }

    when (screen) {
        "home" -> HomeScreen(
            { screen = "recruit" },
            { screen = "mission" },
            { screen = "simulator" },
            { screen = "stats" }
        )
        "recruit" -> RecruitScreen { screen = "home" }
        "mission" -> MissionScreen { screen = "home" }
        "simulator" -> SimulatorScreen { screen = "home" }
        "stats" -> StatsScreen { screen = "home" }
    }
}

@Composable
fun HomeScreen(
    onRecruit: () -> Unit,
    onMission: () -> Unit,
    onSimulator: () -> Unit,
    onStats: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("🚀 Nova Colony", style = MaterialTheme.typography.headlineLarge)

        RedButton("Recruit Crew", onRecruit)
        RedButton("Simulator", onSimulator)
        RedButton("Mission Control", onMission)
        RedButton("Statistics", onStats)
    }
}

@Composable
fun RecruitScreen(onBack: () -> Unit) {

    var name by remember { mutableStateOf("") }
    var selectedSpec by remember { mutableStateOf("Pilot") }
    var refresh by remember { mutableStateOf(0) }

    val specs = listOf("Pilot", "Engineer", "Medic", "Scientist", "Soldier")
    val crewList = remember(refresh) { MainActivity.manager.getAllCrew() }

    Column(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item { Text("Recruit Crew", style = MaterialTheme.typography.headlineMedium) }

            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Crew Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                DropdownMenuBox(specs, selectedSpec) { selectedSpec = it }
            }

            item {
                RedButton("Create Crew") {
                    if (name.isNotEmpty()) {
                        MainActivity.manager.recruit(name, selectedSpec)
                        name = ""
                        refresh++
                    }
                }
            }

            item { Text("Crew List") }

            items(crewList) { crew ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(crew.getName())
                        Text("Skill: ${crew.getSkill()}")
                        Text("Energy: ${crew.getEnergy()}")

                        Spacer(modifier = Modifier.height(8.dp))

                        RedButton("Send to Simulator") {
                            if (!MainActivity.simulatorList.contains(crew)) {
                                MainActivity.simulatorList.add(crew)
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            RedButton("Restore Energy") {
                MainActivity.manager.restoreAllEnergy()
                refresh++
            }

            RedButton("Back", onBack)
        }
    }
}

@Composable
fun SimulatorScreen(onBack: () -> Unit) {

    val simList = MainActivity.simulatorList
    var trigger by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Simulator", style = MaterialTheme.typography.headlineMedium)

        trigger

        simList.forEach {
            Text("${it.getName()} | Skill: ${it.getSkill()} | XP: ${it.getXp()}")
        }

        RedButton("Train All") {
            simList.forEach { it.train() }
            trigger++
        }

        RedButton("Return to Quarters") {
            simList.clear()
            trigger++
        }

        RedButton("Back", onBack)
    }
}

@Composable
fun MissionScreen(onBack: () -> Unit) {

    val crewList = MainActivity.manager.getAllCrew()

    var selectedA by remember { mutableStateOf<CrewMember?>(null) }
    var selectedB by remember { mutableStateOf<CrewMember?>(null) }
    var log by remember { mutableStateOf("No mission yet") }
    var refresh by remember { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        refresh

        item { Text("Mission Control", style = MaterialTheme.typography.headlineMedium) }

        item { Text("Select Crew A") }

        items(crewList) { crew ->
            CrewSelectableCard(crew, crew == selectedA) { selectedA = crew }
            Text("Energy: ${crew.getEnergy()}")
        }

        item { Text("Select Crew B") }

        items(crewList) { crew ->
            CrewSelectableCard(crew, crew == selectedB) { selectedB = crew }
            Text("Energy: ${crew.getEnergy()}")
        }

        item {
            Text("A: ${selectedA?.getName() ?: "None"}")
            Text("B: ${selectedB?.getName() ?: "None"}")
        }

        item {
            RedButton("Start Mission") {

                log = if (selectedA == null || selectedB == null) {
                    "Select both crew!"
                } else if (selectedA == selectedB) {
                    "Choose different crew!"
                } else {
                    MainActivity.missionCount++

                    val result = MissionSystem.runMission(
                        selectedA!!,
                        selectedB!!,
                        MainActivity.missionCount
                    )

                    if (result.contains("SUCCESS")) {
                        MainActivity.winCount++
                    }

                    MainActivity.manager.removeDeadCrew()

                    refresh++
                    result
                }
            }
        }

        item {
            Card {
                Text(log, modifier = Modifier.padding(12.dp))
            }
        }

        item {
            RedButton("Back", onBack)
        }
    }
}

@Composable
fun StatsScreen(onBack: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Statistics", style = MaterialTheme.typography.headlineMedium)

        Text("Crew: ${MainActivity.manager.getCrewCount()}")
        Text("Missions: ${MainActivity.missionCount}")
        Text("Wins: ${MainActivity.winCount}")

        RedButton("Back", onBack)
    }
}

@Composable
fun CrewSelectableCard(
    crew: CrewMember,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFCDD2) else Color(0xFFE0E0E0)
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Column {
                Text(crew.getName())
                Text("Skill: ${crew.getSkill()} | Energy: ${crew.getEnergy()}")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onClick) { Text("Select") }
        }
    }
}

@Composable
fun DropdownMenuBox(options: List<String>, selected: String, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        RedButton(selected) { expanded = true }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun RedButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
    ) {
        Text(text)
    }
}