package shadowverse.cards.Necromancer;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import shadowverse.cards.AbstractBanPool;
import shadowverse.cards.BanGroup;
import shadowverse.cards.Necromancer.Burial.*;
import shadowverse.cards.Necromancer.Default.*;
import shadowverse.cards.Necromancer.Ghosts.*;
import shadowverse.cards.Necromancer.LastWords.*;
import shadowverse.cards.Necromancer.Mech.*;
import shadowverse.cards.Necromancer.Natura.*;
import shadowverse.cards.Necromancer.Necromancy.*;
import shadowverse.cards.Necromancer.Shadows.*;
import shadowverse.characters.Necromancer;

import java.util.ArrayList;

public class NecroPool extends AbstractBanPool {
    static String ID;
    public static CardColor color;
    static ArrayList<BanGroup> pool;
    static int groupCount;
    static int activeCount;

    public NecroPool(int code) {
        super(getName(code), getImg(code), color, code);
    }

    public static String getName(int code) {
        return CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[code];
    }

    public static String getImg(int code) {
        return "img/cards/" + pool.get(code).imgName + ".png";
    }

    public ArrayList<BanGroup> getPool() {
        return pool;
    }

    @Override
    public int getGroupCount() {
        return groupCount;
    }

    @Override
    public int getActiveCount() {
        return activeCount;
    }

    public AbstractCard makeCopy() {
        return new NecroPool(this.code);
    }

    public AbstractBanPool copy(int code) {
        return new NecroPool(code);
    }

    static {
        ID = "shadowverse:NecroPool";
        color = Necromancer.Enums.COLOR_PURPLE;
        pool = new ArrayList<>();
        BanGroup Default = new BanGroup("Luna");
        BanGroup Burial = new BanGroup("Myroel");
        BanGroup Ghosts = new BanGroup("MasqueradeGhost");
        BanGroup LastWords = new BanGroup("Istyndet");
        BanGroup Mech = new BanGroup("Aenea");
        BanGroup Natura = new BanGroup("Lubelle");
        BanGroup Necromancy = new BanGroup("Hector");
        BanGroup Shadows = new BanGroup("Gremory");
        Default.addCard(new ApostleOfSilence());
        Default.addCard(new AttendentOfNight());
        Default.addCard(new Ceres());
        Default.addCard(new Ceridwen());
        Default.addCard(new DeathNote());
        Default.addCard(new DiscipleOfSilence());
        Default.addCard(new ImmortalThane());
        Default.addCard(new ImpiousResurrection());
        Default.addCard(new LadyGray());
        Default.addCard(new Luna());
        Default.addCard(new Mordecai());
        Default.addCard(new NecroAssassin());
        Default.addCard(new Nephthys());
        Default.addCard(new OmenOfSilence());
        Default.addCard(new SonataOfSilence());
        Default.addCard(new SoulConversion());
        Default.addCard(new SowDeathReapLife());
        Default.addCard(new Wight());
        Default.addCard(new Cerberus());
        Default.addCard(new HungrySlash());
        Default.addCard(new AdherentOfScream());
        Default.addCard(new DeathBreath());
        Default.addCard(new ResentfulScreaming());
        Default.addCard(new SoulTaker());
        Default.addCard(new Cernunnos());
        Burial.addCard(new CarnivalNecromancer());
        Burial.addCard(new DemonicProcession());
        Burial.addCard(new EverdarkStrix());
        Burial.addCard(new Leeds());
        Burial.addCard(new Memento());
        Burial.addCard(new Myroel());
        Burial.addCard(new SepticShrink());
        Burial.addCard(new SpiritCurator());
        Burial.addCard(new TheLovers());
        Burial.addCard(new WardenOfCorpses());
        Ghosts.addCard(new Arcus());
        Ghosts.addCard(new Baccherus());
        Ghosts.addCard(new Ferry());
        Ghosts.addCard(new GhostHunter());
        Ghosts.addCard(new MasqueradeGhost());
        Ghosts.addCard(new Miyako(0));
        Ghosts.addCard(new NecroImpulse());
        Ghosts.addCard(new TrickDullahan());
        Ghosts.addCard(new GhastlyBanishment());
        Ghosts.addCard(new GhostRider());
        LastWords.addCard(new AbyssalColonel());
        LastWords.addCard(new Chris());
        LastWords.addCard(new FallenSergeant());
        LastWords.addCard(new Istyndet());
        LastWords.addCard(new Kagero());
        LastWords.addCard(new OtherworldGatekeeper());
        LastWords.addCard(new SkullFish());
        LastWords.addCard(new UnderworldLieutenant());
        LastWords.addCard(new WickedRebirth());
        LastWords.addCard(new Cornelius());
        Mech.addCard(new Aenea());
        Mech.addCard(new BoneDominator());
        Mech.addCard(new BoneDrone());
        Mech.addCard(new DeadMetalStar());
        Mech.addCard(new ForbiddenAndBalance());
        Mech.addCard(new FriendsForever());
        Mech.addCard(new GigantSkull());
        Mech.addCard(new RegenerateSpirit());
        Mech.addCard(new IrongearCorpsman());
        Mech.addCard(new Nicola());
        Natura.addCard(new Lubelle());
        Natura.addCard(new LurchingCorpse());
        Natura.addCard(new RevenantRam());
        Natura.addCard(new ReviveMana());
        Natura.addCard(new Thoth());
        Natura.addCard(new ZombieDog());
        Natura.addCard(new Andrealphus());
        Natura.addCard(new SoulGuide());
        Natura.addCard(new Aisha());
        Natura.addCard(new DeathParty());
        Necromancy.addCard(new Anisage());
        Necromancy.addCard(new DeathscytheHound());
        Necromancy.addCard(new Fafnir());
        Necromancy.addCard(new Hector());
        Necromancy.addCard(new Krampus());
        Necromancy.addCard(new Charon());
        Necromancy.addCard(new Orthrus());
        Necromancy.addCard(new WightKing());
        Necromancy.addCard(new HellfireStrike());
        Necromancy.addCard(new TrothCurse());
        Shadows.addCard(new GloamingTombs());
        Shadows.addCard(new DeathTyrant());
        Shadows.addCard(new Gremory());
        Shadows.addCard(new Hades());
        Shadows.addCard(new HinterlandGhoul());
        Shadows.addCard(new Minthe());
        Shadows.addCard(new PrinceCatacomb());
        Shadows.addCard(new SpartoiSoldier());
        Shadows.addCard(new FoulTempest());
        Shadows.addCard(new BoneChimera());

        pool.add(Default);
        pool.add(Burial);
        pool.add(Ghosts);
        pool.add(LastWords);
        pool.add(Mech);
        pool.add(Natura);
        pool.add(Necromancy);
        pool.add(Shadows);
        groupCount = pool.size();
        activeCount = 5;
    }
}
