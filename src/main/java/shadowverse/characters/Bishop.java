package shadowverse.characters;

import basemod.animations.AbstractAnimation;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import shadowverse.cards.Bishop.Basic.BlackenedScripture;
import shadowverse.cards.Bishop.Basic.Defend_B;
import shadowverse.cards.Bishop.Basic.PriestOfTheCudgel;
import shadowverse.cards.Bishop.Basic.Strike_B;
import shadowverse.cards.Bishop.BishopPool;
import shadowverse.effect.ShadowverseEnergyOrb;
import shadowverse.patch.CharacterSelectScreenPatches;

import java.util.ArrayList;

public class Bishop extends AbstractShadowversePlayer{
    public static class Enums
    {
        @SpireEnum
        public static PlayerClass Bishop;
        @SpireEnum(name = "BISHOP_WHITE_COLOR")
        public static AbstractCard.CardColor COLOR_WHITE;
        @SpireEnum(name = "BISHOP_WHITE_COLOR")
        public static CardLibrary.LibraryType TYPE_WHITE;


    }

    public static final CharacterStrings charStrings = CardCrawlGame.languagePack.getCharacterString("shadowverse:Bishop");

    private static Texture BASE_LAYER = new Texture("img/ui/layer_bishop.png");

    public Bishop(String name) {
        super(name, Enums.Bishop, new ShadowverseEnergyOrb(null, null,null,BASE_LAYER), (AbstractAnimation)new SpriterAnimation(((CharacterSelectScreenPatches.characters[5]).skins[(CharacterSelectScreenPatches.characters[5]).reskinCount]).scmlURL));
        initializeClass(null, ((CharacterSelectScreenPatches.characters[5]).skins[(CharacterSelectScreenPatches.characters[5]).reskinCount]).SHOULDER1, ((CharacterSelectScreenPatches.characters[5]).skins[(CharacterSelectScreenPatches.characters[5]).reskinCount]).SHOULDER2, ((CharacterSelectScreenPatches.characters[5]).skins[(CharacterSelectScreenPatches.characters[5]).reskinCount]).CORPSE, getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(3));
        this.cardPool= new BishopPool(0);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> starterDeck = new ArrayList<>(); int i;
        for (i = 0; i < 4; i++) {
            starterDeck.add(Strike_B.ID);
        }
        for (i = 0; i < 4; i++) {
            starterDeck.add(Defend_B.ID);
        }
        starterDeck.add(BlackenedScripture.ID);
        starterDeck.add(PriestOfTheCudgel.ID);
        return starterDeck;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("shadowverse:Offensive7");
        UnlockTracker.markRelicAsSeen("shadowverse:Offensive7");
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(charStrings.NAMES[0], charStrings.TEXT[0], 70, 70, 5, 99, 5,
                (AbstractPlayer)this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return charStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.COLOR_WHITE;
    }

    @Override
    public Color getCardRenderColor() {
        return CardHelper.getColor(239, 236, 186);
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return (AbstractCard)new BlackenedScripture();
    }

    @Override
    public Color getCardTrailColor() {
        return CardHelper.getColor(239, 236, 186);
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        ((CharacterSelectScreenPatches.characters[5]).skins[(CharacterSelectScreenPatches.characters[5]).reskinCount]).playSelect();
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "AUTOMATON_ORB_SPAWN";
    }

    @Override
    public String getLocalizedCharacterName() {
        return charStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return (AbstractPlayer)new Bishop(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return SpireHeart.DESCRIPTIONS[8];
    }

    @Override
    public Color getSlashAttackColor() {
        return CardHelper.getColor(239, 236, 186);
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY };
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        ((CharacterSelectScreenPatches.characters[5]).skins[(CharacterSelectScreenPatches.characters[5]).reskinCount]).playHurtSound(lastDamageTaken);
    }

}
