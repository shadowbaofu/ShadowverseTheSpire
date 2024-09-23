package shadowverse.cards.Nemesis;


import shadowverse.cards.Nemesis.Artifact.ArtifactImpulse;
import shadowverse.cards.Nemesis.Artifact.GenesisArtifact;
import shadowverse.cards.Nemesis.Basic.DimensionCut;
import shadowverse.cards.Nemesis.Basic.MagisteelLion;
import shadowverse.cards.Nemesis.Condemned.BlackrustUnderling;
import shadowverse.cards.Nemesis.Condemned.Cutthroat;
import shadowverse.cards.Nemesis.Condemned.IronforgedRightHand;
import shadowverse.cards.Nemesis.Condemned.SmeltworkBodyguard;
import shadowverse.cards.Nemesis.Default.RebelAgainstFate;
import shadowverse.cards.Nemesis.Default.Windup;
import shadowverse.cards.Nemesis.Pile.Automation;
import shadowverse.cards.Nemesis.Pile.DeusExMachina;
import shadowverse.cards.Nemesis.Pile.Icarus;
import shadowverse.cards.Nemesis.Puppet.DollsOwner;
import shadowverse.cards.Nemesis.Puppet.Noa;
import shadowverse.cards.Nemesis.Puppet.Orchid;
import shadowverse.cards.Nemesis.Puppet.PuppeteerStrings;
import shadowverse.cards.Nemesis.Resonance.Cassim;
import shadowverse.cards.Nemesis.Resonance.MagnaZero;
import shadowverse.cards.Nemesis.Resonance.Metaproduction;
import shadowverse.cards.Nemesis.Resonance.Rosa;
import shadowverse.cards.Nemesis.Tokens.OmenOfDestruction;
import shadowverse.cards.Neutral.Neutral.*;
import shadowverse.helper.StartPack;

public class NemesisStarterPack {
    public static StartPack artifact1;
    public static StartPack condemned;
    public static StartPack artifact2;
    public static StartPack puppet;
    public static StartPack resonance;
    public static StartPack token;
 

    static {
        artifact1 = new StartPack(new ArtifactImpulse(), new GenesisArtifact());
        condemned = new StartPack(new IronforgedRightHand(), new Cutthroat());
        artifact2 = new StartPack(new Automation(), new DeusExMachina());
        puppet = new StartPack(new Noa(), new Orchid());
        resonance = new StartPack(new RebelAgainstFate(), new MagnaZero());
        token = new StartPack(new RebelAgainstFate(),new OmenOfDestruction());


        artifact1.addCard(new AngelicSnipe(),2);
        artifact1.addCard(new Goliath(), 3);
        artifact1.addCard(new CloudChorus(), 3);
        artifact1.addCard(new MagisteelLion(),2);
        artifact1.addCard(new DimensionCut());


        condemned.addCard(new Goblin());
        condemned.addCard(new AngelicSnipe());
        condemned.addCard(new Goliath());
        condemned.addCard(new AngelicSwordMaiden());
        condemned.addCard(new AngelicBarrage());;
        condemned.addCard(new CloudChorus());
        condemned.addCard(new AngelOfTheWord());
        condemned.addCard(new AngelKnight());
        condemned.addCard(new DimensionCut());
        condemned.addCard(new SmeltworkBodyguard());
        condemned.addCard(new BlackrustUnderling());


        artifact2.addCard(new Goliath(), 3);
        artifact2.addCard(new CloudChorus(), 3);
        artifact2.addCard(new AngelicSwordMaiden());
        artifact2.addCard(new MagisteelLion(),2);
        artifact2.addCard(new Icarus(),2);


        puppet.addCard(new Goliath(), 3);
        puppet.addCard(new CloudChorus(), 3);
        puppet.addCard(new ShieldAngel(),2);
        puppet.addCard(new DimensionCut());
        puppet.addCard(new PuppeteerStrings());
        puppet.addCard(new DollsOwner());


        resonance.addCard(new Goliath(), 3);
        resonance.addCard(new CloudChorus(), 3);
        resonance.addCard(new MagisteelLion());
        resonance.addCard(new DimensionCut());
        resonance.addCard(new Metaproduction());
        resonance.addCard(new Cassim());
        resonance.addCard(new Rosa());

        token.addCard(new Goliath(), 3);
        token.addCard(new CloudChorus(), 3);
        token.addCard(new ShieldAngel(),2);
        token.addCard(new MagisteelLion());
        token.addCard(new PuppeteerStrings());
        token.addCard(new Windup());


    }
}
