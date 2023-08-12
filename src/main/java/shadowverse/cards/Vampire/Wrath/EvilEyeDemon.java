package shadowverse.cards.Vampire.Wrath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Vampire;


public class EvilEyeDemon
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:EvilEyeDemon";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:EvilEyeDemon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/EvilEyeDemon.png";

    public EvilEyeDemon() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Vampire.Enums.COLOR_SCARLET, CardRarity.UNCOMMON, CardTarget.ENEMY, 1, CardType.SKILL);
        this.baseDamage = 9;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void applyPowers() {
        AbstractShadowversePlayer w = (AbstractShadowversePlayer) AbstractDungeon.player;
        if (w.wrathCount > 0) {
            this.magicNumber = w.wrathCount * this.baseMagicNumber;
            super.applyPowers();
            this.isMagicNumberModified = (this.magicNumber != this.baseMagicNumber);
        } else {
            super.applyPowers();
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("EvilEyeDemon"));
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("EvilEyeDemon_Acc"));
        addToBot(new LoseHPAction(p, p, 1));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.POISON));
    }

    public AbstractCard makeCopy() {
        return new EvilEyeDemon();
    }
}


