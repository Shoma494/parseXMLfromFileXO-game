import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadXmlStAXCursorParser {

    //private static final String FILENAME = "src/Игрок1_VS_Игрок2_20220316_035825.xml";

    public static void main(String[] args) throws IOException {
        System.out.println("Введите имя файла в корневом каталоге src");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String filename = reader.readLine();
        filename = "src/"+filename;

        try {
            printXmlByXmlCursorReader(Paths.get(filename));

        } catch (FileNotFoundException | XMLStreamException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printXmlByXmlCursorReader(Path path)
            throws FileNotFoundException, XMLStreamException, InterruptedException {
        PlayingField playingField = new PlayingField();
        String[] steps = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(
                new FileInputStream(path.toFile()));

        int eventType = reader.getEventType();


        while (reader.hasNext()) {

            eventType = reader.next();

            if (eventType == XMLEvent.START_ELEMENT) {

                switch (reader.getName().getLocalPart()) {

                    case "Step":
                        String num = reader.getAttributeValue(null, "num");
                        String playerId = reader.getAttributeValue(null, "playerId");

                        eventType = reader.next();

                        if (eventType == XMLEvent.CHARACTERS) {
                            if (playerId.equals("1")) {
                                steps[Integer.parseInt(reader.getText())-1]="X";
                                playingField.playingField(steps);
                                Thread.sleep(500);
                            }

                            if (playerId.equals("2")) {
                                steps[Integer.parseInt(reader.getText())-1]="0";
                                playingField.playingField(steps);
                                Thread.sleep(500);
                            }
                        }
                        break;

                    case "GameResult":
                        eventType = reader.next();
                        if (eventType == XMLEvent.START_ELEMENT) {
                            String winnerId = reader.getAttributeValue(null, "id");
                            String winnerName = reader.getAttributeValue(null, "name");
                            String winnerSymbol = reader.getAttributeValue(null, "symbol");
                            System.out.println("Player " + winnerId + " -> " + winnerName + " is winner as '" + winnerSymbol + "'!");

                        }
                        else  System.out.println(reader.getText());


                        break;
                }

            }

        }

    }

}