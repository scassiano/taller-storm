package spouts;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class SendPhrasesSpout extends BaseRichSpout {

    private static final String[] phrases = {
            "Puedo escribir los versos más tristes esta noche,",
            "Escribir por ejemplo La noche está estrellada,",
            "y tiritan azules los astros a lo lejos",
            "Puedo escribir los versos más tristes esta noche",
            "Yo la quise y a veces ella también me quiso",
            "En las noches como ésta la tuve entre mis brazos",
            "La besé tantas veces bajo el cielo infinito",
            "Ella me quiso a veces yo también la quería",
            "Cómo no haber amado sus grandes ojos fijos",
            "Puedo escribir los versos más tristes esta noche",
            "Piensa en mí que soy así, como te decía",
            "La noche está estrellada y ella no está conmigo",
            "Este es el último dolor que ella me causa",
            "Y estos son los últimos versos que le escribo"
    };

    private SpoutOutputCollector collector;

    @Override
    public void open(final Map conf, final TopologyContext context, final SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        int rnd = new Random().nextInt(phrases.length);
        collector.emit(Collections.singletonList(phrases[rnd]));
    }

    @Override
    public void declareOutputFields(final OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("phrase"));
    }

}
