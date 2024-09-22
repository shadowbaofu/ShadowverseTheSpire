package shadowverse.cards.Elf;

import shadowverse.cards.Elf.Basic.FairyWhisperer;
import shadowverse.cards.Elf.Basic.SylvanJustice;
import shadowverse.cards.Elf.Default.AirboundBarrage;
import shadowverse.cards.Elf.Default.BlossomTreant;
import shadowverse.cards.Elf.Default.ServantOfUnkilling;
import shadowverse.cards.Elf.Fairy.Aria;
import shadowverse.cards.Elf.Fairy.Verdant;
import shadowverse.cards.Elf.Left.Sekka;
import shadowverse.cards.Elf.Left.WoodOfBrambles;
import shadowverse.cards.Elf.Long.ElfGuard;
import shadowverse.cards.Elf.Long.Rhinoceroach;
import shadowverse.cards.Elf.Return.Wimael;
import shadowverse.cards.Elf.Short.BuddingInitiate;
import shadowverse.cards.Elf.Short.LeafshadeAssassin;
import shadowverse.cards.Elf.Short.Magachiyo;
import shadowverse.cards.Elf.Wood.CrossCombination;
import shadowverse.cards.Elf.Wood.GreenWoodGuardian;
import shadowverse.cards.Elf.Wood.Lymaga;
import shadowverse.cards.Elf.Wood.WoodlandCleaver;
import shadowverse.cards.Neutral.Neutral.*;
import shadowverse.helper.StartPack;

public class ElfStarterPack {
    public static StartPack fairy;
    public static StartPack sekka;
    public static StartPack combo1;
    public static StartPack bounce;
    public static StartPack combo2;
    public static StartPack wood;

    static {
        fairy = new StartPack(new Verdant(), new Aria());
        sekka = new StartPack(new BlossomTreant(), new Sekka());
        combo1 = new StartPack(new WoodOfBrambles(), new Rhinoceroach());
        bounce = new StartPack(new ServantOfUnkilling(), new Wimael());
        combo2 = new StartPack(new LeafshadeAssassin(), new Magachiyo());
        wood = new StartPack(new CrossCombination(), new Lymaga());

        fairy.addCard(new Goblin(),2);
        fairy.addCard(new Goliath(), 3);
        fairy.addCard(new CloudChorus(),3);
        fairy.addCard(new ShieldAngel());
        fairy.addCard(new FairyWhisperer());
        fairy.addCard(new SylvanJustice());


        sekka.addCard(new Goblin(), 2);
        sekka.addCard(new Goliath(), 3);
        sekka.addCard(new CloudChorus(), 3);
        sekka.addCard(new ShieldAngel());
        sekka.addCard(new FairyWhisperer(),2);

        combo1.addCard(new AngelicSnipe());
        combo1.addCard(new Goblin(), 2);
        combo1.addCard(new Goliath(), 2);
        combo1.addCard(new CloudChorus(), 3);
        combo1.addCard(new Lyrial());
        combo1.addCard(new FairyWhisperer());
        combo1.addCard(new ElfGuard());

        bounce.addCard(new AngelicSnipe());
        bounce.addCard(new Goblin());
        bounce.addCard(new Goliath(), 3);
        bounce.addCard(new CloudChorus(), 3);
        bounce.addCard(new ShieldAngel());
        bounce.addCard(new FairyWhisperer());
        bounce.addCard(new AirboundBarrage());

        combo2.addCard(new AngelicSnipe());
        combo2.addCard(new Goblin(), 2);
        combo2.addCard(new Goliath(), 2);
        combo2.addCard(new CloudChorus(), 3);
        combo2.addCard(new Lyrial());
        combo2.addCard(new FairyWhisperer());
        combo2.addCard(new BuddingInitiate());

        wood.addCard(new Goliath(), 3);
        wood.addCard(new CloudChorus(),3);
        wood.addCard(new AngelicSwordMaiden());
        wood.addCard(new WoodlandCleaver(),2);
        wood.addCard(new GreenWoodGuardian(),2);
    }
}
