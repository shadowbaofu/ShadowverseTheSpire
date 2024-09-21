package shadowverse.helper;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Discovery;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowverse.Shadowverse;
import shadowverse.cards.Dragon.Discard1.RockbackAnkylosaurus;
import shadowverse.cards.Elf.NatMech.ScaryTrend;
import shadowverse.cards.Nemesis.Mech.Armadillo;
import shadowverse.cards.Neutral.Neutral.*;
import shadowverse.cards.Royal.NatMech.BrothersUnited;
import shadowverse.cards.Royal.NatMech.Cybercannoneer;
import shadowverse.cards.Royal.RoyalStartPack;
import shadowverse.cards.Vampire.NatMech.MechaforgeDevil;
import shadowverse.cards.Witch.Natura.Aeroelementalist;
import shadowverse.cards.Witch.WitchStarterPack;
import shadowverse.characters.*;

import java.util.ArrayList;

public class StartPackHelper {

    public static StartPack nat;
    public static StartPack mech;
    public static StartPack uni;

    public static ArrayList<AbstractCard> getPack() {
        ArrayList<StartPack> packs = new ArrayList<>();
        packs.add(uni);
        if (AbstractDungeon.player.chosenClass == Elf.Enums.Elf) {
            if (Shadowverse.groupActive[4]) {
                packs.add(nat);
                packs.add(mech);
            }
        } else if (AbstractDungeon.player.chosenClass == Royal.Enums.Royal) {
            if (Shadowverse.groupActive[2]) {
                packs.add(RoyalStartPack.evolve);
            }
            if (Shadowverse.groupActive[3]) {
                packs.add(RoyalStartPack.festive);
            }
            if (Shadowverse.groupActive[4]) {
                packs.add(RoyalStartPack.hero);
            }
            if (Shadowverse.groupActive[5]) {
                packs.add(RoyalStartPack.levin);
            }
            if (Shadowverse.groupActive[6]) {
                packs.add(RoyalStartPack.loot);
            }
            if (Shadowverse.groupActive[7] || Shadowverse.groupActive[9]) {
                packs.add(RoyalStartPack.rally);
            }
            if (Shadowverse.groupActive[8]) {
                packs.add(nat);
                packs.add(mech);
            }
        } else if (AbstractDungeon.player.chosenClass == Witchcraft.Enums.WITCHCRAFT) {
            if (Shadowverse.groupActive[1]){
                packs.add(WitchStarterPack.academic);
            }
            if (Shadowverse.groupActive[2]){
                packs.add(WitchStarterPack.chess);
            }
            if (Shadowverse.groupActive[3]){
                packs.add(WitchStarterPack.earth1);
            }
            if (Shadowverse.groupActive[4]){
                packs.add(WitchStarterPack.earth2);
            }
            if (Shadowverse.groupActive[5]) {
                packs.add(mech);
            }
            if (Shadowverse.groupActive[6]) {
                packs.add(nat);
            }
            if (Shadowverse.groupActive[7]){
                packs.add(WitchStarterPack.spellboost1);
            }
            if (Shadowverse.groupActive[8]){
                packs.add(WitchStarterPack.spellboost2);
            }
        } else if (AbstractDungeon.player.chosenClass == Dragon.Enums.Dragon) {
            if (Shadowverse.groupActive[4]) {
                packs.add(nat);
            }
        } else if (AbstractDungeon.player.chosenClass == Necromancer.Enums.Necromancer) {
            if (Shadowverse.groupActive[4]) {
                packs.add(mech);
            }
            if (Shadowverse.groupActive[5]) {
                packs.add(nat);
            }
        } else if (AbstractDungeon.player.chosenClass == Vampire.Enums.Vampire) {
            if (Shadowverse.groupActive[5]) {
                packs.add(nat);
                packs.add(mech);
            }
        } else if (AbstractDungeon.player.chosenClass == Bishop.Enums.Bishop) {
            if (Shadowverse.groupActive[1] || Shadowverse.groupActive[2]) {
                packs.add(nat);
            }
            if (Shadowverse.groupActive[4]) {
                packs.add(mech);
            }
        } else if (AbstractDungeon.player.chosenClass == Nemesis.Enums.Nemesis) {
            if (Shadowverse.groupActive[3]) {
                packs.add(mech);
            }
        }
        int roll = AbstractDungeon.cardRandomRng.random(packs.size() - 1);
        return packs.get(roll).getPack();
    }

    static {
        uni = new StartPack(new SlashOfOne(),new OmenOfOne());
        mech = new StartPack(new DualAngle(),new Machinus());
        nat = new StartPack(new DualAngle(),new Machinus());

        uni.addCard(new Goblin());
        uni.addCard(new AngelicSnipe());
        uni.addCard(new Goliath());
        uni.addCard(new AngelicSwordMaiden());
        uni.addCard(new AngelicBarrage());
        uni.addCard(new CloudChorus());
        uni.addCard(new Lyrial());
        uni.addCard(new AngelOfTheWord());
        uni.addCard(new HealingAngel());
        uni.addCard(new AngelKnight());
        uni.addCard(new ShieldAngel());

        mech.addCard(new Goblin());
        mech.addCard(new MechaGoblin(),3);
        mech.addCard(new Goliath(),2);
        mech.addCard(new AngelicSwordMaiden());
        mech.addCard(new ShieldAngel());
        mech.addCard(new Cybercannoneer());
        mech.addCard(new MechaforgeDevil());
        mech.addCard(new Armadillo());

        nat.addCard(new AngelicSnipe(),1);
        nat.addCard(new AngelicBarrage(),1);
        nat.addCard(new AngelOfTheWord());
        nat.addCard(new CloudChorus(),1);
        nat.addCard(new Lyrial());
        nat.addCard(new TravelersRespite(),2);
        nat.addCard(new ScaryTrend());
        nat.addCard(new BrothersUnited());
        nat.addCard(new Aeroelementalist());
        nat.addCard(new RockbackAnkylosaurus());
    }
}
