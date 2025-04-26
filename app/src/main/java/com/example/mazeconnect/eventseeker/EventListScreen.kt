
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.compose.foundation.clickable
import com.example.mazeconnect.components.EventSeekerBottomNavigation
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.runtime.*
import com.example.mazeconnect.eventorganizer.Event
import kotlinx.coroutines.tasks.await





data class Event(
    val name: String,
    val description: String,
    val date: String,
    val location: String,
  //  val price: String
)

@Composable
fun EventList(navController: NavHostController) {
    val database = FirebaseDatabase.getInstance().reference
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        try {
            val snapshot = database.child("events").get().await()
            events = snapshot.children.mapNotNull { child ->
                child.getValue(Event::class.java)?.copy(id = child.key ?: "")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Explore Events",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            )

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (events.isEmpty()) {
                Text(
                    text = "No events available.",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Gray
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(events) { event ->
                        EventCard(eventName = event.name)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            EventSeekerBottomNavigation(navController)
        }
    }
}


@Composable
fun EventCard(eventName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(100.dp)
            .clickable {
          //      navController.navigate("event_details/${event.name}/${event.description}/${event.date}/${event.location}}")

            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6A0DAD)),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                eventName,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventList() {
    val navController = rememberNavController()
    EventList(navController)
}