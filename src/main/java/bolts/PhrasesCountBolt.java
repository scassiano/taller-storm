package bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PhrasesCountBolt extends BaseRichBolt {

    private final Map<String, Integer> wordCounts = new HashMap<>();
    private long toFileAfter = 0;
    private int taskId;

    @Override
    public void prepare(final Map stormConf, final TopologyContext context, final OutputCollector collector) {
        taskId = context.getThisTaskId();
    }

    @Override
    public void execute(final Tuple input) {
        final String phrase = input.getStringByField("phrase");
        if (wordCounts.containsKey(phrase)) {
            wordCounts.put(phrase, wordCounts.get(phrase) + 1);
        } else {
            wordCounts.put(phrase, 0);
        }

        toFileAfter++;

        if (toFileAfter % 50L == 0L) {
            final String fileContent = wordCounts.entrySet().stream().map(wc -> "Phrase: " + wc.getKey() + "| Count: " + wc.getValue()).
                    collect(Collectors.joining(System.lineSeparator()));
            try {
                Files.write(Paths.get("Task" + taskId + ".txt"), fileContent.getBytes());
            } catch (final IOException e) {
                System.out.println("No se pudo crear el archivo");
            }
        }
    }

    @Override
    public void declareOutputFields(final OutputFieldsDeclarer declarer) {
    }

}
