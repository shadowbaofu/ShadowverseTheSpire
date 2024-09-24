package shadowverse.cards.Dragon;

import shadowverse.cards.Dragon.Armed.Draconir;
import shadowverse.cards.Dragon.Armed.IronscaleSerpentDrake;
import shadowverse.cards.Dragon.Armed.LavateinnDragon;
import shadowverse.cards.Dragon.Armed.SwiftbladeDragonewt;
import shadowverse.cards.Dragon.Basic.DragonOracle;
import shadowverse.cards.Dragon.Basic.DragonWarrior;
import shadowverse.cards.Dragon.Buff.CoralShark;
import shadowverse.cards.Dragon.Buff.Dracu;
import shadowverse.cards.Dragon.Buff.PropheticDragon;
import shadowverse.cards.Dragon.Buff.Sandstorm;
import shadowverse.cards.Dragon.Default.BlazingBreath;
import shadowverse.cards.Dragon.Default.Rahab;
import shadowverse.cards.Dragon.Default.SeabrandDragon;
import shadowverse.cards.Dragon.Default.SiLong;
import shadowverse.cards.Dragon.Discard1.ImperialDragoon;
import shadowverse.cards.Dragon.Discard1.Kit;
import shadowverse.cards.Dragon.Discard1.ScaleboundPlight;
import shadowverse.cards.Dragon.Discard1.WildfangDragonewt;
import shadowverse.cards.Dragon.Discard2.*;
import shadowverse.cards.Dragon.Tempo.*;
import shadowverse.cards.Neutral.Neutral.*;
import shadowverse.helper.StartPack;

public class DragonStarterPack {
    public static StartPack armed;
    public static StartPack discard1;
    public static StartPack discard2;
    public static StartPack ramp;
    public static StartPack tempo;
    public static StartPack buff;
 

    static {
        armed = new StartPack(new Draconir(), new LavateinnDragon());
        discard1 = new StartPack(new WildfangDragonewt(), new ImperialDragoon());
        discard2 = new StartPack(new GoldenDragonDen(), new Lumiore());
        ramp = new StartPack(new Rahab(), new SiLong());
        tempo = new StartPack(new AdherentOfArdor(), new OmenOfDisdain());
        buff = new StartPack(new PropheticDragon(),new Dracu());


        armed.addCard(new Goliath(), 3);
        armed.addCard(new CloudChorus(), 3);
        armed.addCard(new DragonWarrior());
        armed.addCard(new BlazingBreath());
        armed.addCard(new IvoryDragon());
        armed.addCard(new SwiftbladeDragonewt());
        armed.addCard(new IronscaleSerpentDrake());



        discard1.addCard(new Goliath(), 3);
        discard1.addCard(new CloudChorus(), 3);
        discard1.addCard(new AngelicSwordMaiden());
        discard1.addCard(new DragonOracle(),2);
        discard1.addCard(new Kit());
        discard1.addCard(new ScaleboundPlight());


        discard2.addCard(new Goliath(), 3);
        discard2.addCard(new CloudChorus(), 3);
        discard2.addCard(new BlazingBreath());
        discard2.addCard(new DragonOracle(),2);
        discard2.addCard(new DragonewtMight(),2);


        ramp.addCard(new Goliath(), 3);
        ramp.addCard(new CloudChorus(), 3);
        ramp.addCard(new DragonOracle(),3);
        ramp.addCard(new BlazingBreath());
        ramp.addCard(new SeabrandDragon());


        tempo.addCard(new Goliath(), 3);
        tempo.addCard(new CloudChorus(), 3);
        tempo.addCard(new DragonWarrior());
        tempo.addCard(new IvoryDragon());
        tempo.addCard(new DiscipleOfDisdain(),2);
        tempo.addCard(new ServantOfDisdain());

        buff.addCard(new Goliath(), 3);
        buff.addCard(new CloudChorus(), 3);
        buff.addCard(new AngelicSwordMaiden());
        buff.addCard(new DragonOracle());
        buff.addCard(new DragonWarrior());
        buff.addCard(new CoralShark());
        buff.addCard(new Sandstorm());


    }
}
