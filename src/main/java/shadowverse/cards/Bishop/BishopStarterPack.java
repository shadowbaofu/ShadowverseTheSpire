package shadowverse.cards.Bishop;

import shadowverse.cards.Bishop.Amulet1.AngelRat;
import shadowverse.cards.Bishop.Amulet1.GoldenCity;
import shadowverse.cards.Bishop.Amulet1.SneakAttack;
import shadowverse.cards.Bishop.Amulet1.WardenOfTheWings;
import shadowverse.cards.Bishop.Amulet2.HallowedDogma;
import shadowverse.cards.Bishop.Amulet2.JudgmentSpearMaster;
import shadowverse.cards.Bishop.Amulet2.Selena;
import shadowverse.cards.Bishop.Basic.BlackenedScripture;
import shadowverse.cards.Bishop.Basic.PriestOfTheCudgel;
import shadowverse.cards.Bishop.Default.BeastlyVow;
import shadowverse.cards.Bishop.Default.SacredPlea;
import shadowverse.cards.Bishop.Evil.MoriaeEncomium;
import shadowverse.cards.Bishop.Evil.SkullFane;
import shadowverse.cards.Bishop.Evil.TarnishedGrail;
import shadowverse.cards.Bishop.Recover1.Elana;
import shadowverse.cards.Bishop.Recover1.KLT;
import shadowverse.cards.Bishop.Recover1.Tenko;
import shadowverse.cards.Bishop.Recover2.Elluvia;
import shadowverse.cards.Bishop.Recover2.Kira;
import shadowverse.cards.Bishop.Recover2.PureflameLady;
import shadowverse.cards.Bishop.Ward.Shiro;
import shadowverse.cards.Bishop.Ward.Wilbert;
import shadowverse.cards.Neutral.Neutral.*;
import shadowverse.helper.StartPack;

public class BishopStarterPack {
    public static StartPack amulet1;
    public static StartPack amulet2;
    public static StartPack evil;
    public static StartPack recover1;
    public static StartPack recover2;
    public static StartPack ward;
 

    static {
        amulet1 = new StartPack(new WardenOfTheWings(), new GoldenCity());
        amulet2 = new StartPack(new JudgmentSpearMaster(), new Selena());
        evil = new StartPack(new TarnishedGrail(), new SkullFane());
        recover1 = new StartPack(new KLT(), new Elana());
        recover2 = new StartPack(new Tenko(), new Elluvia());
        ward = new StartPack(new Shiro(),new Wilbert());


        amulet1.addCard(new Goliath(), 3);
        amulet1.addCard(new CloudChorus(), 3);
        amulet1.addCard(new ShieldAngel(),2);
        amulet1.addCard(new BlackenedScripture());
        amulet1.addCard(new SneakAttack());
        amulet1.addCard(new AngelRat());



        amulet2.addCard(new Goliath(), 3);
        amulet2.addCard(new CloudChorus(), 3);
        amulet2.addCard(new ShieldAngel());
        amulet2.addCard(new PriestOfTheCudgel());
        amulet2.addCard(new SacredPlea());
        amulet2.addCard(new BeastlyVow());
        amulet2.addCard(new HallowedDogma());


        evil.addCard(new Goblin());
        evil.addCard(new AngelicSnipe());
        evil.addCard(new Goliath(), 3);
        evil.addCard(new CloudChorus(), 3);
        evil.addCard(new PriestOfTheCudgel());
        evil.addCard(new BlackenedScripture());
        evil.addCard(new MoriaeEncomium());


        recover1.addCard(new Goliath(), 3);
        recover1.addCard(new CloudChorus(), 3);
        recover1.addCard(new HealingAngel());
        recover1.addCard(new MechaGoblin(),2);
        recover1.addCard(new PriestOfTheCudgel());
        recover1.addCard(new BlackenedScripture());


        recover2.addCard(new Goliath(), 3);
        recover2.addCard(new CloudChorus(), 2);
        recover2.addCard(new MechaGoblin(),2);
        recover2.addCard(new BlackenedScripture());
        recover2.addCard(new PriestOfTheCudgel());
        recover2.addCard(new Kira());
        recover2.addCard(new PureflameLady());

        ward.addCard(new AngelicSnipe(),2);
        ward.addCard(new Goliath(), 3);
        ward.addCard(new CloudChorus(), 3);
        ward.addCard(new AngelicSwordMaiden());
        ward.addCard(new BlackenedScripture());
        ward.addCard(new PriestOfTheCudgel());


    }
}
