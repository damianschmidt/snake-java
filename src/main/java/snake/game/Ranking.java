package snake.game;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Ranking implements UpdatePossible{
    private List<Record> records;

    Ranking() {
        records = new ArrayList<>();
        loadRankingFromFile();
    }

    public void update(Graphics g) {
        if (Game.getInstance().isPaused()){
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(50, 50, Game.getWIDTH() - 100, Game.getHEIGHT() - 100);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("Ranking", 60, 100);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            for (int i = 0; i < 10; i++) {
                Record record = records.get(i);
                String recordName = i + 1 + ". " + record.getName();
                int recordScore = record.getScore();
                g.drawString(recordName, 100, 150 + i * 40);
                g.drawString(String.valueOf(recordScore), 400, 150 + i * 40);
            }
        }
    }

    void addToRanking(Record record) {
        records.add(record);
    }

    private void loadRankingFromFile() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/data/ranking.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length > 1) {
                    int score = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    Record record = new Record(name, score);
                    records.add(record);
                }
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    void saveRankingToFile() {
        records.sort(new Sortbyscore());
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("src/main/data/ranking.txt"))) {
            for (Record record : records)
                pw.println(record.getScore() + " " + record.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void printRanking() {
        for (Record record : records) {
            record.printRecord();
        }
    }
}
