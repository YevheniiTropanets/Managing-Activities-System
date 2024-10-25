package com.yevhenii.organisationSystem.entity;

public class GraphTest {
    //private final int MAX_VERTS = 1000;
//    private final Vertex[] vertexList; //list of vertices
//    private final int vertices;
//    //private final List<Vertex> vertexList;
//    private final int[][] adjMatrix; // adjacency matrix
//    private int numVerts; // current number  of vertices
//    private final boolean[] visited;
//    private final Stack<Integer> stack;
//
//    public GraphTest(int vertices) {
//        this.vertices = vertices;
//        vertexList = new Vertex[vertices];
//        //vertexList = new ArrayList<>();
//        adjMatrix = new int[vertices][vertices];
//        visited = new boolean[vertices];
//        numVerts = 0;
//        stack = new Stack<>();
//        for (int m = 0; m < vertices; m++) {
//            for (int n = 0; n < vertices; n++) {
//                adjMatrix[m][n] = 0;
//            }
//        }
//    }
//
//    public void addVertex(Vertex vertex) {
////        vertexList.add(vertex);
////        numVerts++;
//        vertexList[numVerts++] = vertex;
//    }
//
//    public void addEdge(int startVertex, int endVertex) {
//        adjMatrix[startVertex][endVertex] = 1;
//        adjMatrix[endVertex][startVertex] = 1;
//    }
//
//    public void displayVertex(int position) {
//        //System.out.println(vertexList.get(position).toString());
//        System.out.println(vertexList[position].toString());
//    }
//
//    public void displayAdjMatrix() {
////        for (int m = 0; m < vertexList.size(); m++) {
////            for (int n = 0; n < vertexList.size(); n++) {
////                System.out.print(" " + adjMatrix[m][n]);
////            }
////            System.out.println("");
////        }
//        for (int m = 0; m < vertices; m++) {
//            for (int n = 0; n < vertices; n++) {
//                System.out.print(" " + adjMatrix[m][n]);
//            }
//            System.out.println();
//        }
//    }
//
//    public void dfs(GraphTest g) { // depth-first search
//        // begin at vertex 0
//        vertexList[0].wasVisited = true; // mark it
//        displayVertex(0); // display it
//        stack.push(0); // push it
//        while (!stack.isEmpty()) // until stack empty,
//        {// get an unvisited vertex adjacent to stack top
//            int v = getAdjUnvisitedVertex(stack.peek());
//            if (v == -1) stack.pop(); // if no such vertex,
//            else {                    // if it exists
//                vertexList[v].wasVisited = true; // mark it
//                displayVertex(v); // display it
//                stack.push(v); // push it
//            }
//        } // end while   // stack is empty, so we’re done
//        for (int j = 0; j < numVerts; j++) // reset flags
//            vertexList[j].wasVisited = false;
//    } // end dfs
//
//    public int maxMatching(GraphTest graphTest) {
//        int applicants = numVerts;
//        int jobs = numVerts;
//
//        int assign[] = new int[jobs];    //an array to track which job is assigned to which applicant
//        for (int i = 0; i < jobs; i++)
//            assign[i] = -1;    //initially set all jobs are available
//        int jobCount = 0;
//
//        for (int applicant = 0; applicant < applicants; applicant++) {    //for all applicants
//            //for each applicant, all jobs will be not visited initially
//            boolean visited[] = new boolean[jobs];
//
//            //check if applicant can get a job
//            if (bipartiteMatch(graphTest, applicant, visited, assign)) {
//                //if yes then increase the job count
//                jobCount++;
//            }
//        }
//        return jobCount;
//    }
//
//    boolean bipartiteMatch(GraphTest graphTest, int applicant, boolean[] visited, int[] assign) {
//        //check each job for the applicant
//        for (int job = 0; job < numVerts; job++) {
//            //check if applicant can do this job means adjMatrix[applicant][job] == 1
//            // and applicant has not considered for this job before, means visited[job]==false
//            if (graphTest.adjMatrix[applicant][job] == 1 && !visited[job]) {
//                //mark as job is visited, means applicant is considered for this job
//                visited[job] = true;
//                //now check if job was not assigned earlier - assign it to this applicant
//                // OR job was assigned earlier to some other applicant 'X' earlier then
//                //make recursive call for applicant 'X' to check if some other job can be assigned
//                // so that this job can be assigned to current candidate.
//                int assignedApplicant = assign[job];
//                if (assignedApplicant < 0 || bipartiteMatch(graphTest, assignedApplicant, visited, assign)) {
//                    assign[job] = applicant;    //assign job job to applicant applicant
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//
//
//
//
//    public int getAdjUnvisitedVertex(int v) {
//        for (int j = 0; j < numVerts; j++)
//            if (adjMatrix[v][j] == 1 && !vertexList[j].wasVisited)
//                return j;
//        return -1;
//    }
//
//    public int getNumVerts() {
//        return numVerts;
//    }
//
//    public void setNumVerts(int numVerts) {
//        this.numVerts = numVerts;
//    }
}
