
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


data class Event(
    val name: String,
    val description: String,
    val date: String,
    val location: String,
    val price: String
)

@Composable
fun EventList(navController: NavHostController) {


    val events = listOf(
        Event("Tech Conference", "A gathering of tech enthusiasts.", "Dec 10, 2025", "Nairobi", "Ksh 1,000"),
        Event("Music Festival", "Live music and fun.", "Nov 20, 2025", "Mombasa", "Ksh 2,500"),
        Event("Art Exhibition", "Showcasing local artists.", "Oct 15, 2025", "Kisumu", "Ksh 800"),
        Event("Sports Tournament", "Football & Basketball event.", "Sep 5, 2025", "Nakuru", "Ksh 1,500"),
        Event("Cooking Class", "Learn new recipes.", "Aug 22, 2025", "Eldoret", "Ksh 500")
    )

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

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(events) { event ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        EventCard(navController = navController, event = event)
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
fun EventCard(navController: NavHostController, event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(100.dp)
            .clickable {
                navController.navigate("event_details/${event.name}/${event.description}/${event.date}/${event.location}/${event.price}")
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
                text = event.name,
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