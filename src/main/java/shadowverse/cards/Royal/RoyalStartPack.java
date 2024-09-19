package shadowverse.cards.Royal;

import shadowverse.cards.Neutral.Neutral.*;
import shadowverse.cards.Royal.Ambush.ShadowedMemories;
import shadowverse.cards.Royal.Basic.FloralFencer;
import shadowverse.cards.Royal.Basic.OathlessKnight;
import shadowverse.cards.Royal.Default.Ilmisuna;
import shadowverse.cards.Royal.Default.Sera;
import shadowverse.cards.Royal.Evolve.*;
import shadowverse.cards.Royal.Festive.FrontDeskFrog;
import shadowverse.cards.Royal.Festive.HonorableThief;
import shadowverse.cards.Royal.Festive.OpulentStrategist;
import shadowverse.cards.Royal.Festive.SuaveBandit;
import shadowverse.cards.Royal.Hero.FlameSoldier;
import shadowverse.cards.Royal.Hero.IronwroughtDefender;
import shadowverse.cards.Royal.Hero.ValiantFencer;
import shadowverse.cards.Royal.Hero.Windslasher;
import shadowverse.cards.Royal.Levin.Albert;
import shadowverse.cards.Royal.Levin.LevinArcher;
import shadowverse.cards.Royal.Levin.LevinBeastmaster;
import shadowverse.cards.Royal.Levin.LevinScholar;
import shadowverse.cards.Royal.Loot.Rogers;
import shadowverse.cards.Royal.Loot.UsurpingSpineblade;
import shadowverse.cards.Royal.Minion.EmpressOfSerenity;
import shadowverse.cards.Royal.Minion.FrontlineInstructor;
import shadowverse.cards.Royal.Minion.SageCommander;
import shadowverse.cards.Royal.Rally.StrikeproneGuardian;
import shadowverse.helper.StartPack;

public class RoyalStartPack {
    public static StartPack evolve;
    public static StartPack festive;
    public static StartPack hero;
    public static StartPack levin;
    public static StartPack loot;
    public static StartPack rally;

    static {
        evolve = new StartPack(new LuminousMage(), new Seofon());
        festive = new StartPack(new FrontDeskFrog(), new OpulentStrategist());
        hero = new StartPack(new Windslasher(), new ValiantFencer());
        levin = new StartPack(new LevinScholar(), new Albert());
        loot = new StartPack(new WeeMerchantsAppraisal(), new Rogers());
        rally = new StartPack(new EmpressOfSerenity(), new Sera());

        evolve.addCard(new Goblin(), 2);
        evolve.addCard(new AngelicSwordMaiden(), 2);
        evolve.addCard(new FloralFencer(), 2);
        evolve.addCard(new Charlotta(), 2);
        evolve.addCard(new ShadowedMemories());
        evolve.addCard(new WeeMerchantsAppraisal());
        evolve.addCard(new Ilmisuna());

        festive.addCard(new AngelicSnipe(), 2);
        festive.addCard(new AngelicBarrage(), 2);
        festive.addCard(new Goliath(), 2);
        festive.addCard(new CloudChorus(), 3);
        festive.addCard(new HonorableThief());
        festive.addCard(new SuaveBandit());

        hero.addCard(new Goblin(), 2);
        hero.addCard(new Goliath(), 2);
        hero.addCard(new AngelicSwordMaiden(), 2);
        hero.addCard(new CloudChorus(), 2);
        hero.addCard(new FlameSoldier(), 2);
        hero.addCard(new IronwroughtDefender());

        levin.addCard(new Goblin(), 2);
        levin.addCard(new Goliath(), 2);
        levin.addCard(new AngelicSwordMaiden(), 2);
        levin.addCard(new CloudChorus(), 2);
        levin.addCard(new LevinArcher(), 2);
        levin.addCard(new LevinBeastmaster());

        loot.addCard(new AngelicBarrage(), 2);
        loot.addCard(new Goliath(), 2);
        loot.addCard(new AngelicSwordMaiden(), 2);
        loot.addCard(new CloudChorus(), 2);
        loot.addCard(new UsurpingSpineblade(), 2);
        loot.addCard(new AdherentOfHollow());

        rally.addCard(new Goliath(), 2);
        rally.addCard(new AngelicSwordMaiden(), 2);
        rally.addCard(new FloralFencer(), 2);
        rally.addCard(new OathlessKnight(), 2);
        rally.addCard(new StrikeproneGuardian());
        rally.addCard(new FrontlineInstructor());
        rally.addCard(new SageCommander());
    }
}
