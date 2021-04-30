/*
* Title: hw3_2.java
* Abstract: This program reads in graph data and presents a path for 
            the Traveling Salesman problem.
* Author: Annie Center
* ID: 8392
* Date: 03/23/2021
*/

import java.util.ArrayList;
import java.util.Scanner;

class Edge {
    private String v1;
    private String v2;
    private int weight;

    public Edge(String v1,String v2) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = 0;
    }

    public Edge(String v1, String v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public String getV1() {
        return v1;
    }

    public String getV2() {
        return v2;
    }

    public int getWeight() {
        return weight;
    }

    public void setV1(String v) {
        v1 = v;
    }

    public void setV2(String v) {
        v2 = v;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean equals(Edge e) {
        if(this.getV1().equals(e.getV1()) && this.getV2().equals(e.getV2())) {
            return true;
        }
        return false;
    }

    public String toString() {
        return v1 + "->" + v2;
    }
}

class Path {
    private String[] cities;
    private int cost;
    private int length;

    public Path() {
        cities = new String[0];
        this.cost = cost;
        length = -1;
    }

    public Path(int cost) {
        cities = new String[0];
        this.cost = cost;
        length = 0;
    }

    public Path(String[] cities) {
        this.cities = new String[cities.length];
        length = cities.length;
        for(int i = 0; i < length; i++) {
            this.cities[i] = cities[i];
        }
        cost = 0;
    }

    public String getCity(int index) {
        return cities[index];
    }

    public void setCity(int index, String newCity) {
        cities[index] = newCity;
    }

    public void addCity(int index, String city) {
        String[] tmp = new String[length + 1];
        for(int i = 0; i < index; i++) {
            tmp[i] = cities[i];
        }
        tmp[index] = city;
        for(int i = index; i < length; i++) {
            tmp[i+1] = cities[i];
        }
        cities = tmp;
        length++;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void addCost(int weight) {
        cost += weight;
    }

    public int length() {
        return length;
    }

    public String toString() {
        String s = "";

        if(length == 0) {
            return "";
        }

        for(int i = 0; i < length; i++) {
            if(i == length - 1)
                s += cities[i];
            else
                s += cities[i] + "->";
        }

        return s;
    }
}

public class hw3_2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        String first = input.next();
        String[] cities = new String[n-1];
        for(int i = 0; i < n-1; i++) {
            cities[i] = input.next();
        }
        int m = input.nextInt();
        Path path = new Path(1000000);
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for(int i = 0; i < m; i++) {
            edges.add(new Edge(input.next(), input.next(), input.nextInt()));
        }

        ArrayList<Path> paths = getAllPermutations(cities);

        for(Path p : paths) {
            p.addCity(0, first);
            p.addCity(n, first);
        }

        //System.out.println();

        for(int i = 0; i < paths.size(); i++) {
            boolean keptPath = false;
            //System.out.println("Path:" + paths.get(i));
            for(int j = 0; j < n; j++) {
                String v1 = paths.get(i).getCity(j);
                String v2 = paths.get(i).getCity(j+1);
                Edge tmp = new Edge(v1, v2);
                //System.out.println(containsEdge(edges, tmp));
                int hasEdge = containsEdge(edges, tmp);
                if(hasEdge > -1) {
                    int weight = edges.get(hasEdge).getWeight();
                    paths.get(i).addCost(weight);
                    //System.out.println(weight);
                    keptPath = true;
                }
                else {
                    paths.remove(i);
                    if(i > 0) i--;
                    //System.out.println("Removed Path");
                    keptPath = false;
                    break;
                }
            }
            if(keptPath && paths.get(i).getCost() < path.getCost() && paths.get(i).getCost() != 0) {
                path = paths.get(i);
            }
        }

        //for(Path p : paths) System.out.println("Path:" + p + "\nCost:" + p.getCost());

        //System.out.println();
        if(path.getCost() == 1000000) {
            System.out.println("Path:" + path + "\nCost:" + -1);
        }
        else {
            System.out.println("Path:" + path + "\nCost:" + path.getCost());
        }

    }

    public static int containsEdge(ArrayList<Edge> edges, Edge edge) {
        for(Edge e : edges) {
            if(e.equals(edge)) {
                return edges.indexOf(e);
            }
        }
        return -1;
    }

    public static void swap(String[] input, int a, int b) {
        String tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    public static ArrayList<Path> getAllPermutations(String[] input) {
        int n = input.length;
        ArrayList<Path> paths = new ArrayList<Path>();
        int[] indexes = new int[n];
        for (int i = 0; i < n; i++) {
            indexes[i] = 0;
        }

        paths.add(new Path(input));

        int i = 0;
        while (i < n) {
            if (indexes[i] < i) {
                swap(input, i % 2 == 0 ?  0: indexes[i], i);
                paths.add(new Path(input));
                indexes[i]++;
                i = 0;
            }
            else {
                indexes[i] = 0;
                i++;
            }
        }

        return paths;
    }
}