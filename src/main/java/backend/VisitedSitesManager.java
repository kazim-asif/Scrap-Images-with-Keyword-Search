//package backend;
//
//import java.io.*;
//import java.util.HashSet;
//import java.util.Set;
//
//public class VisitedSitesManager {
//    private String visitedSitesFilePath;
//    private Set<String> visitedSites;
//
//    public VisitedSitesManager(String visitedSitesFilePath) {
//        this.visitedSitesFilePath = visitedSitesFilePath;
//        this.visitedSites = new HashSet<>();
//    }
//    
//    public Set<String> getVisitedSites() {
//		return visitedSites;
//	}
//
//	public void setVisitedSites(Set<String> visitedSites) {
//		this.visitedSites = visitedSites;
//	}
//
//	public void loadVisitedSites() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(visitedSitesFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                visitedSites.add(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void saveVisitedSites() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(visitedSitesFilePath))) {
//            for (String site : visitedSites) {
//                writer.write(site);
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean isVisited(String url) {
//        return visitedSites.contains(url);
//    }
//
//    public void addVisitedSite(String url) {
//    	visitedSites.add(url);
//    	System.out.print("");
//    }
//}



package backend;

import java.io.*;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class VisitedSitesManager {
    private final String filePath;
    private final Set<String> visitedSites;

    public VisitedSitesManager(String filePath) {
        this.filePath = filePath;
        this.visitedSites = new HashSet<>();
        loadVisitedSites();
    }

    public void loadVisitedSites() {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.lines(path).forEach(visitedSites::add);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addVisitedSite(String url) {
        if (visitedSites.add(url)) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                writer.write(url);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveVisitedSites() {
      
    }

    public synchronized boolean isVisited(String url) {
        return visitedSites.contains(url);
    }
}
