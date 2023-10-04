package shadowverse.cards.Elf.Default;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import shadowverse.action.DestroyAction;
import shadowverse.characters.Elf;
import shadowverse.characters.Necromancer;


public class SaplingSteward extends CustomCard {
    public static final String ID = "shadowverse:SaplingSteward";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SaplingSteward");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SaplingSteward.png";
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/SaplingSteward_L.png");

    public SaplingSteward() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 5;
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(2);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false)))
            addToBot(new SFXAction("SaplingSteward_L"));
        else
            addToBot(new SFXAction("SaplingSteward"));
        addToBot(new DestroyAction(2, new DrawCardAction(this.magicNumber)));
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new SaplingSteward();
    }
}
