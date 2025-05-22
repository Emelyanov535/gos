import java.util.*;

public class Graph<V> {
    // Карта: вершина -> Map соседей и веса ребер
    private final Map<V, Map<V, Integer>> adjacencyMap;

    public Graph() {
        adjacencyMap = new HashMap<>();
    }

    // Добавить вершину
    public void addVertex(V vertex) {
        adjacencyMap.putIfAbsent(vertex, new HashMap<>());
    }

    // Удалить вершину и все связанные ребра
    public void removeVertex(V vertex) {
        adjacencyMap.values().forEach(neighbors -> neighbors.remove(vertex));
        adjacencyMap.remove(vertex);
    }

    // Добавить ребро (ориентированное) с весом
    public void addEdge(V from, V to, int weight) {
        addVertex(from);
        addVertex(to);
        adjacencyMap.get(from).put(to, weight);
    }

    // Удалить ребро
    public void removeEdge(V from, V to) {
        Map<V, Integer> neighbors = adjacencyMap.get(from);
        if (neighbors != null) {
            neighbors.remove(to);
        }
    }

    // Есть ли вершина
    public boolean containsVertex(V vertex) {
        return adjacencyMap.containsKey(vertex);
    }

    // Есть ли ребро
    public boolean containsEdge(V from, V to) {
        Map<V, Integer> neighbors = adjacencyMap.get(from);
        return neighbors != null && neighbors.containsKey(to);
    }

    // Получить вес ребра, если есть, иначе null
    public Integer getEdgeWeight(V from, V to) {
        Map<V, Integer> neighbors = adjacencyMap.get(from);
        if (neighbors == null) return null;
        return neighbors.get(to);
    }

    // Получить соседей (смежные вершины) данной вершины
    public Set<V> getNeighbors(V vertex) {
        Map<V, Integer> neighbors = adjacencyMap.get(vertex);
        if (neighbors == null) return Collections.emptySet();
        return neighbors.keySet();
    }

    // Обход в глубину (DFS) начиная с вершины start
    public Set<V> dfs(V start) {
        Set<V> visited = new LinkedHashSet<>();
        Stack<V> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            V vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (V neighbor : getNeighbors(vertex)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return visited;
    }

    // Обход в ширину (BFS) начиная с вершины start
    public Set<V> bfs(V start) {
        Set<V> visited = new LinkedHashSet<>();
        Queue<V> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            V vertex = queue.poll();
            for (V neighbor : getNeighbors(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return visited;
    }

    // Для отладки: печать графа
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<V, Map<V, Integer>> entry : adjacencyMap.entrySet()) {
            sb.append(entry.getKey()).append(" -> ");
            for (Map.Entry<V, Integer> e : entry.getValue().entrySet()) {
                sb.append("(").append(e.getKey()).append(", w=").append(e.getValue()).append(") ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // --- Тест ---
    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();

        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "C", 3);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 7);
        graph.addEdge("D", "A", 1);

        System.out.println("Graph:\n" + graph);

        System.out.println("Neighbors of A: " + graph.getNeighbors("A"));
        System.out.println("Contains vertex B? " + graph.containsVertex("B"));
        System.out.println("Contains edge A->B? " + graph.containsEdge("A", "B"));
        System.out.println("Weight of edge A->C: " + graph.getEdgeWeight("A", "C"));

        System.out.println("DFS from A: " + graph.dfs("A"));
        System.out.println("BFS from A: " + graph.bfs("A"));

        graph.removeEdge("A", "C");
        System.out.println("After removing edge A->C:\n" + graph);

        graph.removeVertex("C");
        System.out.println("After removing vertex C:\n" + graph);
    }
}
