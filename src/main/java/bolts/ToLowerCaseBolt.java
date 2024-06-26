package bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.Collections;
import java.util.Map;

public class ToLowerCaseBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(final Map stormConf, final TopologyContext context, final OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(final Tuple input) {
        collector.emit(Collections.singletonList(input.getStringByField("phrase").toLowerCase()));
    }

    @Override
    public void declareOutputFields(final OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("phrase"));
    }

}
