package com.example.madprojectprac;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class navigation extends AppCompatActivity {


    private Spinner startSpinner;
    private Spinner destinationSpinner;
    private ImageView storeMapImage; // Reference to the store map image
    private ImageView movingMarker; // Reference to the moving marker
    private Map<String, Map<String, Integer>> storeGraph;
    Button homebut;
    Button lstbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        // Initialize UI components
        startSpinner = findViewById(R.id.start_spinner);
        destinationSpinner = findViewById(R.id.destination_spinner);
        storeMapImage = findViewById(R.id.store_map_image);
        movingMarker = findViewById(R.id.moving_marker); // Reference to the marker ImageView
        Button startNavigationButton = findViewById(R.id.start_navigation);
        homebut = findViewById(R.id.homebutton);
        lstbtn = findViewById(R.id.listbutton);

        lstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(navigation.this, finalshoppinglist.class);
                startActivity(intent);
            }
        });

        homebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(navigation.this, home_pg.class);
                startActivity(intent);
            }
        });


        // Setup location spinner options
        String[] locations = {
                "Entrance", "Dairy", "Confectionery", "Florists",
                "Fruits", "Vegetables", "Seafood",
                "Meat", "Bakery", "Beverages",
                "Checkouts", "Other"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        startSpinner.setAdapter(adapter);
        destinationSpinner.setAdapter(adapter);


        // Define the store layout as a graph
        setupStoreGraph();


        // Start navigation when button is clicked
        startNavigationButton.setOnClickListener(view -> {
            String startLocation = startSpinner.getSelectedItem().toString();
            String destinationLocation = destinationSpinner.getSelectedItem().toString();


            // Calculate the shortest path using Dijkstra's algorithm
            List<String> shortestPath = calculateShortestPath(startLocation, destinationLocation);


            // Visualize the path on the store map with animation
            visualizePathOnStoreMap(shortestPath);
        });
    }


    private void setupStoreGraph() {
        storeGraph = new HashMap<>();


        // Initialize nodes and their connections
        String[] locations = {
                "Entrance", "Dairy", "Confectionery",
                 "Florists",
                "Fruits", "Vegetables", "Seafood",
                "Meat", "Bakery", "Beverages",
                "Checkouts", "Other"
        };


        // Initialize neighbors for each location
        for (String location : locations) {
            storeGraph.put(location, new HashMap<>());
        }


        // Existing connections (as per your provided code)
        // Entrance

        storeGraph.get("Entrance").put("Florists", 0);
        storeGraph.get("Entrance").put("Checkouts", 0);
        storeGraph.get("Entrance").put("Other", 0);


        // Dairy
        Map<String, Integer> dairyNeighbors = new HashMap<>();

        dairyNeighbors.put("Meat", 0);
        dairyNeighbors.put("Beverages", 0);
        dairyNeighbors.put("Other", 0);
        storeGraph.put("Dairy", dairyNeighbors);


        // Confectionery
        Map<String, Integer> confectioneryNeighbors = new HashMap<>();

        confectioneryNeighbors.put("Bakery", 0);
        confectioneryNeighbors.put("Checkouts", 0);
        confectioneryNeighbors.put("Other", 0);
        storeGraph.put("Confectionery", confectioneryNeighbors);


        // Florists
        Map<String, Integer> floristsNeighbors = new HashMap<>();
        floristsNeighbors.put("Entrance", 0);
        floristsNeighbors.put("Vegetables", 0);
        floristsNeighbors.put("Other", 0);
        storeGraph.put("Florists", floristsNeighbors);


        // Fruits
        Map<String, Integer> fruitsNeighbors = new HashMap<>();

        fruitsNeighbors.put("Vegetables", 0);
        fruitsNeighbors.put("Seafood", 0);
        fruitsNeighbors.put("Other", 0);
        storeGraph.put("Fruits", fruitsNeighbors);


        // Vegetables
        Map<String, Integer> vegetablesNeighbors = new HashMap<>();

        vegetablesNeighbors.put("Florists", 0);
        vegetablesNeighbors.put("Fruits", 0);
        vegetablesNeighbors.put("Other", 0);
        storeGraph.put("Vegetables", vegetablesNeighbors);


        // Seafood
        Map<String, Integer> seafoodNeighbors = new HashMap<>();

        seafoodNeighbors.put("Fruits", 0);
        seafoodNeighbors.put("Meat", 0);
        seafoodNeighbors.put("Other", 0);
        storeGraph.put("Seafood", seafoodNeighbors);


        // Meat
        Map<String, Integer> meatNeighbors = new HashMap<>();

        meatNeighbors.put("Dairy", 0);
        meatNeighbors.put("Seafood", 0);
        meatNeighbors.put("Other", 0);
        storeGraph.put("Meat", meatNeighbors);


        // Bakery
        Map<String, Integer> bakeryNeighbors = new HashMap<>();

        bakeryNeighbors.put("Confectionery", 0);
        bakeryNeighbors.put("Beverages", 0);
        bakeryNeighbors.put("Other", 0);
        storeGraph.put("Bakery", bakeryNeighbors);


        // Beverages
        Map<String, Integer> beveragesNeighbors = new HashMap<>();

        beveragesNeighbors.put("Dairy", 0);
        beveragesNeighbors.put("Bakery", 0);
        beveragesNeighbors.put("Other", 0);
        storeGraph.put("Beverages", beveragesNeighbors);


        // Checkouts
        Map<String, Integer> checkoutsNeighbors = new HashMap<>();
        checkoutsNeighbors.put("Entrance", 0);
        checkoutsNeighbors.put("Confectionery", 0);
        checkoutsNeighbors.put("Other", 0);
        storeGraph.put("Checkouts", checkoutsNeighbors);


        // Other
        Map<String, Integer> otherNeighbors = new HashMap<>();
        otherNeighbors.put("Entrance", 00);
        otherNeighbors.put("Dairy", 0);
        otherNeighbors.put("Confectionery", 0);
        otherNeighbors.put("Florists", 0);
        otherNeighbors.put("Fruits", 0);
        otherNeighbors.put("Vegetables", 0);
        otherNeighbors.put("Seafood", 0);
        otherNeighbors.put("Meat", 0);
        otherNeighbors.put("Bakery", 0);
        otherNeighbors.put("Beverages", 0);
        otherNeighbors.put("Checkouts", 0);
        storeGraph.put("Other", otherNeighbors);
    }


    private List<String> calculateShortestPath(String start, String destination) {
        // Implement Dijkstra's algorithm to find the shortest path
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));


        // Initialize distances
        for (String node : storeGraph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
        }
        distances.put(start, 0);
        queue.offer(start);


        while (!queue.isEmpty()) {
            String currentNode = queue.poll();


            if (currentNode.equals(destination)) {
                break; // Reached the destination
            }


            for (Map.Entry<String, Integer> neighbor : storeGraph.get(currentNode).entrySet()) {
                String neighborNode = neighbor.getKey();
                int weight = neighbor.getValue();
                int newDist = distances.get(currentNode) + weight;


                if (newDist < distances.get(neighborNode)) {
                    distances.put(neighborNode, newDist);
                    previous.put(neighborNode, currentNode);
                    queue.offer(neighborNode);
                }
            }
        }


        // Backtrack to find the path
        List<String> path = new ArrayList<>();
        for (String at = destination; at != null; at = previous.get(at)) {
            path.add(at);
        }
        java.util.Collections.reverse(path); // Reverse the path to get the correct order
        return path;
    }


    private void visualizePathOnStoreMap(List<String> path) {
        // Example implementation of visualization (you can adjust this)
        // Assuming you have an image view to show the store map


        // Hide the moving marker initially
        movingMarker.setVisibility(View.INVISIBLE);


        // Prepare to animate the moving marker
        Handler handler = new Handler();
        final int delay = 3000; // Delay in milliseconds between moves
        final int[] currentStep = {0};


        // Create a runnable to move the marker
        Runnable moveMarkerRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentStep[0] < path.size()) {
                    String location = path.get(currentStep[0]);
                    moveMarkerToLocation(location);
                    currentStep[0]++;
                    handler.postDelayed(this, delay);
                } else {
                    movingMarker.setVisibility(View.INVISIBLE); // Hide after finishing
                }
            }
        };


        // Show the marker and start the animation
        movingMarker.setVisibility(View.VISIBLE);
        handler.post(moveMarkerRunnable);
        animateMarker(path, 0);
    }


    private void moveMarkerToLocation(String location) {
        // Implement your logic to move the marker on the store map
        // This is a placeholder for where you would set the position of the moving marker based on location coordinates


        Point coords = getLocationCoordinates(location);
        if (coords != null) {
            movingMarker.setX(coords.x);
            movingMarker.setY(coords.y);
        }
    }
    private void animateMarker(List<String> path, int index) {
        if (index >= path.size() - 1) {
            // Animation finished
            return;
        }




        Point startPoint = getLocationCoordinates(path.get(index));
        Point endPoint = getLocationCoordinates(path.get(index + 1));




        if (startPoint != null && endPoint != null) {
            // Move the marker to the starting point
            movingMarker.setX(startPoint.x);
            movingMarker.setY(startPoint.y);




            // Create an animation to move the marker to the end point
            movingMarker.animate()
                    .x(endPoint.x)
                    .y(endPoint.y)
                    .setDuration(3000) // Duration of the animation
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            // Delay before moving to the next segment
                            new Handler().postDelayed(() -> animateMarker(path, index + 1), 3000); // 1 second delay
                        }
                    })
                    .start();
        }
    }




    private Point getLocationCoordinates(String location) {

        switch (location) {
            case "Entrance":
                return new Point(260, 1750); // Example coordinates
            case "Dairy":
                return new Point(675, 900);
            case "Confectionery":
                return new Point(875, 1380);
            case "Florists":
                return new Point(130, 1650);
            case "Fruits":
                return new Point(130, 1050);
            case "Vegetables":
                return new Point(130, 1300);
            case "Seafood":
                return new Point(130, 900);
            case "Meat":
                return new Point(370, 900);
            case "Bakery":
                return new Point(875, 1150);
            case "Beverages":
                return new Point(875, 950);
            case "Checkouts":
                return new Point(950, 1750);
            case "Other":
                return new Point(470, 1250);
            default:
                return null; // Location not found
        }
    }


}
