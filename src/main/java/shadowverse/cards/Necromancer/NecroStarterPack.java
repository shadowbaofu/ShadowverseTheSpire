package shadowverse.cards.Necromancer;

import shadowverse.cards.Necromancer.Basic.SpartoiSergent;
import shadowverse.cards.Necromancer.Basic.UndyingResentment;
import shadowverse.cards.Necromancer.Burial.Myroel;
import shadowverse.cards.Necromancer.Default.HungrySlash;
import shadowverse.cards.Necromancer.Ghosts.Baccherus;
import shadowverse.cards.Necromancer.Ghosts.GhostHunter;
import shadowverse.cards.Necromancer.Ghosts.MasqueradeGhost;
import shadowverse.cards.Necromancer.LastWords.Apple;
import shadowverse.cards.Necromancer.LastWords.Chris;
import shadowverse.cards.Necromancer.Mech.RegenerateSpirit;
import shadowverse.cards.Necromancer.Necromancy.Hector;
import shadowverse.cards.Necromancer.Shadows.BoneChimera;
import shadowverse.cards.Necromancer.Shadows.GloamingTombs;
import shadowverse.cards.Necromancer.Shadows.Hades;
import shadowverse.cards.Necromancer.Shadows.SpartoiSoldier;
import shadowverse.cards.Neutral.Neutral.*;
import shadowverse.helper.StartPack;

public class NecroStarterPack {
    public static StartPack burial;
    public static StartPack ghost;
    public static StartPack lastword;
    public static StartPack necromancy;
    public static StartPack shadows;

    static {
        burial = new StartPack(new RegenerateSpirit(), new Myroel());
        ghost = new StartPack(new Baccherus(), new MasqueradeGhost());
        lastword = new StartPack(new Apple(), new Chris());
        necromancy = new StartPack(new GloamingTombs(), new Hector());
        shadows = new StartPack(new GloamingTombs(), new Hades());

        burial.addCard(new Goliath(), 3);
        burial.addCard(new CloudChorus(),3);
        burial.addCard(new ShieldAngel(),2);
        burial.addCard(new SpartoiSergent());
        burial.addCard(new HungrySlash(),2);


        ghost.addCard(new Goliath(), 3);
        ghost.addCard(new CloudChorus(), 3);
        ghost.addCard(new ShieldAngel(),2);
        ghost.addCard(new SpartoiSergent());
        ghost.addCard(new UndyingResentment());
        ghost.addCard(new GhostHunter());


        lastword.addCard(new Goliath(), 3);
        lastword.addCard(new CloudChorus(), 3);
        lastword.addCard(new AngelicBarrage());
        lastword.addCard(new AngelicSwordMaiden());
        lastword.addCard(new SpartoiSergent());
        lastword.addCard(new UndyingResentment());
        lastword.addCard(new BoneChimera());


        necromancy.addCard(new Goblin(),2);
        necromancy.addCard(new Goliath(), 3);
        necromancy.addCard(new CloudChorus(), 3);
        necromancy.addCard(new ShieldAngel());
        necromancy.addCard(new SpartoiSergent());
        necromancy.addCard(new UndyingResentment());

        shadows.addCard(new Goblin(), 2);
        shadows.addCard(new Goliath(), 3);
        shadows.addCard(new CloudChorus(), 3);
        shadows.addCard(new SpartoiSergent(),2);
        shadows.addCard(new SpartoiSoldier());
    }
}
