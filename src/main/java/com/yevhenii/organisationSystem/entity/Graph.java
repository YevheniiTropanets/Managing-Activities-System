package com.yevhenii.organisationSystem.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Graph {
    private final int[][] adjMatrix;
    int numApplications;
    int numVenues;
    List<Venue> venueList;
    List<ApplicationToGetVenue> applicationList;
    List<Edge> edgeList;
    int[] matching;
    Map<Venue, ApplicationToGetVenue> resultMatching;
    List<Edge> resultMatchingEdgeList;
    List<Edge> edgesWithNoVenues;

    // кол-во строк =  кол-во заявок, каждая строка = заявка
    // кол-во столбцов = кол-во залов, номер столбца -> номер зала +1
    // arr[n]  -> application,  значение массива  = номер заявки
    // n - venue,  номер элемента - > номер зала  [100, 200] зал 0 получает заявку 100, зал 1 получает заявку 200.
    public void displayAdjMatrix() {
        for (int m = 0; m < numApplications; m++) {
            for (int n = 0; n < numVenues; n++) {
                System.out.print(" " + adjMatrix[m][n]);
            }
            System.out.println();
        }
    }

    public Graph(List<Edge> edgeList) {
        this.edgeList = edgeList;
        // Extract the distinct applications and venues from the edgeList
        this.applicationList = new ArrayList<>();
        this.venueList = new ArrayList<>();
        this.resultMatchingEdgeList = new ArrayList<>();
        for (Edge edge : edgeList) {
            if (!applicationList.contains(edge.getApplicationToGetVenue())) {
                applicationList.add(edge.getApplicationToGetVenue());
            }
            if (!venueList.contains(edge.getVenue())) {
                venueList.add(edge.getVenue());
            }
        }
        // Set the number of applications and venues
        this.numApplications = applicationList.size();
        this.numVenues = venueList.size();
        this.matching = new int[numVenues];
        // Initialize the adjacency matrix
        this.adjMatrix = new int[numApplications][numVenues];
        this.resultMatching = new HashMap<>();
        // Fill the adjacency matrix based on the edgeList

        for (Edge edge : edgeList) {
            int applicationIndex = applicationList.indexOf(edge.getApplicationToGetVenue());
            int venueIndex = venueList.indexOf(edge.getVenue());
            adjMatrix[applicationIndex][venueIndex] = 1;
        }
    }


    private boolean dfs(int application, boolean[] visited) {
        if (visited[application]) return false;
        visited[application] = true;
        for (int venue = 0; venue < numVenues; venue++) {
            if (adjMatrix[application][venue] == 1 && (matching[venue] == -1 || dfs(matching[venue], visited))) {
                matching[venue] = application;
                return true;
            }
        }
        return false;
    }

    public int[] maximumBipartiteMatching() {
        Arrays.fill(matching, -1);
        for (int application = 0; application < numApplications; application++) {
            boolean[] visited = new boolean[numApplications];
            Arrays.fill(visited, false);
            dfs(application, visited);
        }
        return matching;
    }

    public void createMap() {
        for (int i = 0; i < matching.length; i++) {
            if (matching[i] != -1) {
                resultMatching.put(venueList.get(i), applicationList.get(matching[i]));
            }
        }

    }

    public void createResultEdgeList() {
        for (Edge e : edgeList) {
            for (int i = 0; i < matching.length; i++) {
                if (matching[i] != -1 && e.getApplicationToGetVenue().equals(applicationList.get(matching[i])) && e.getVenue().equals(venueList.get(i))) {
                    resultMatchingEdgeList.add(e);
                }
            }
        }

    }

    public void changeResultListStatus() {
        for (Edge edge : resultMatchingEdgeList) {
            edge.setMatching(true);
        }
    }

    public List<Edge> getApplicationEdgeList(ApplicationToGetVenue application) {
        List<Edge> edgeResultList = new ArrayList<>();
        for (Edge edge : edgeList) {
            if (edge.getApplicationToGetVenue().equals(application)) edgeResultList.add(edge);
        }
        return edgeResultList;
    }

    public List<String> getCityNameOnEdge(List<Edge> applicationEdgeList) {
        List<String> cityNames = new ArrayList<>();
        for(Edge edge : applicationEdgeList){
            cityNames.add(edge.getVenue().getStreet().getCity().getCityName());
        }
        return  cityNames;
    }
    public List<Venue> removeSimilarVenue(List<Venue> venues){
        List<Venue> uniqueVenues = new ArrayList<>(venues);
        Iterator<Venue> it = uniqueVenues.iterator();
        while (it.hasNext()) {
            Venue venue = it.next();
            for (Venue algoVenue : venueList) {
                if (venue.equals(algoVenue)) {
                    it.remove();
                    break;
                }
            }
        }
        return uniqueVenues;
    }


    public List<ApplicationToGetVenue> getApplicationsNotInResultMatching() {
        List<ApplicationToGetVenue> notInMatching = new ArrayList<>();
        for (ApplicationToGetVenue application : applicationList) {
            boolean isMatched = false;

            for (Edge edge : resultMatchingEdgeList) {
                if (edge.getApplicationToGetVenue().equals(application)) {
                    isMatched = true;
                    break;
                }
            }


            if (!isMatched) {
                notInMatching.add(application);
            }
        }
        return notInMatching;
    }


    public void changeStatus() {
        for (Edge edge : edgeList) {
            if (resultMatching.containsKey(edge.getVenue()) && resultMatching.get(edge.getVenue()).equals(edge.getApplicationToGetVenue())) {
                edge.setMatching(true);
            }
        }
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }


}
