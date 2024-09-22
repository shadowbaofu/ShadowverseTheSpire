package shadowverse.cards.Vampire;

import shadowverse.cards.Neutral.Neutral.*;
import shadowverse.cards.Vampire.Academic.Arka;
import shadowverse.cards.Vampire.Academic.DemonEyeGangster;
import shadowverse.cards.Vampire.Academic.Galom;
import shadowverse.cards.Vampire.Avarice.Cradle;
import shadowverse.cards.Vampire.Avarice.HellSpearWarrior;
import shadowverse.cards.Vampire.Avarice.MoonriseWerewolf;
import shadowverse.cards.Vampire.Avarice.ShowdownDemon;
import shadowverse.cards.Vampire.Basic.DarkGeneral;
import shadowverse.cards.Vampire.Basic.RazoryClaw;
import shadowverse.cards.Vampire.Bat.DarkfeastBat;
import shadowverse.cards.Vampire.Bat.NightVampire;
import shadowverse.cards.Vampire.Bat.SummoningBloodkin;
import shadowverse.cards.Vampire.Bat.Veight;
import shadowverse.cards.Vampire.Evolve.CrimsonVirtue;
import shadowverse.cards.Vampire.Evolve.Kali;
import shadowverse.cards.Vampire.Evolve.Yuzuki;
import shadowverse.cards.Vampire.Self.BearBerserk;
import shadowverse.cards.Vampire.Self.ServantOfLust;
import shadowverse.cards.Vampire.Vengeance.DiabolicDrain;
import shadowverse.cards.Vampire.Vengeance.SexyVampire;
import shadowverse.cards.Vampire.Wrath.*;
import shadowverse.helper.StartPack;

public class VampireStarterPack {
    public static StartPack academic;
    public static StartPack avarice;
    public static StartPack bat;
    public static StartPack evolve;
    public static StartPack wrath1;
    public static StartPack vengeance;
    public static StartPack wrath2;

    static {
        academic = new StartPack(new Arka(), new Galom());
        avarice = new StartPack(new ShowdownDemon(), new Cradle());
        bat = new StartPack(new Veight(), new NightVampire());
        evolve = new StartPack(new Kali(), new Yuzuki());
        wrath1 = new StartPack(new SadisticDemon(), new DarkfeastBat());
        vengeance = new StartPack(new DiabolicDrain(),new SexyVampire());
        wrath2 = new StartPack(new BearBerserk(),new Garodeth());


        academic.addCard(new Goblin(),2);
        academic.addCard(new Goliath(), 3);
        academic.addCard(new CloudChorus(),3);
        academic.addCard(new RazoryClaw());
        academic.addCard(new DarkGeneral(),2);


        avarice.addCard(new Goliath(), 3);
        avarice.addCard(new CloudChorus(), 3);
        avarice.addCard(new ShieldAngel());
        avarice.addCard(new RazoryClaw());
        avarice.addCard(new HellSpearWarrior(),2);
        avarice.addCard(new MoonriseWerewolf());


        bat.addCard(new AngelicSnipe());
        bat.addCard(new Goliath(), 3);
        bat.addCard(new CloudChorus(), 3);
        bat.addCard(new ShieldAngel(),2);
        bat.addCard(new RazoryClaw());
        bat.addCard(new SummoningBloodkin());


        evolve.addCard(new Goliath(), 3);
        evolve.addCard(new CloudChorus(), 3);
        evolve.addCard(new Lyrial(),2);
        evolve.addCard(new ShieldAngel(),2);
        evolve.addCard(new CrimsonVirtue());


        wrath1.addCard(new Goliath(), 3);
        wrath1.addCard(new CloudChorus(), 3);
        wrath1.addCard(new AngelicSwordMaiden());
        wrath1.addCard(new RazoryClaw(),2);
        wrath1.addCard(new ServantOfLust());
        wrath1.addCard(new AntelopeWarrior());

        vengeance.addCard(new AngelicSnipe(),2);
        vengeance.addCard(new Goliath(), 3);
        vengeance.addCard(new CloudChorus(), 3);
        vengeance.addCard(new DarkGeneral(),2);
        vengeance.addCard(new DemonEyeGangster());

        wrath2.addCard(new Goliath(), 3);
        wrath2.addCard(new CloudChorus(), 3);
        wrath2.addCard(new AngelicSwordMaiden());
        wrath2.addCard(new RazoryClaw(),2);
        wrath2.addCard(new SteamrollingTank());
        wrath2.addCard(new ViciousBlitzer());
    }
}
