package shadowverse.cards.Bishop.Amulet2;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractCrystalizeCard;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Bishop;
import shadowverse.orbs.AmuletOrb;

public class JudgmentSpearMaster
        extends AbstractAccelerateCard implements AbstractCrystalizeCard {
    public static final String ID = "shadowverse:JudgmentSpearMaster";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:JudgmentSpearMaster");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/JudgmentSpearMaster.png";

    public JudgmentSpearMaster() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.UNCOMMON, CardTarget.ENEMY,1,CardType.POWER);
        this.baseDamage = 15;
        this.baseBlock = 8;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.AMULET_FOR_ONECE);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
            upgradeBlock(2);
        }
    }



    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("JudgmentSpearMaster"));
        addToBot(new GainBlockAction(p,this.block));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("JudgmentSpearMaster_Acc"));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new JudgmentSpearMaster();
    }

    @Override
    public void onStartOfTurn(AmuletOrb paramOrb) {
    }

    @Override
    public void onEvoke(AmuletOrb paramOrb) {
        AbstractCard c = this.makeCopy();
        if (this.upgraded)
            c.upgrade();
        c.setCostForTurn(0);
        c.type = CardType.ATTACK;
        played = true;
        AbstractDungeon.player.hand.addToTop(c);
    }

    @Override
    public void endOfTurn(AmuletOrb paramOrb) {

    }

    @Override
    public void onGainedBlock(int blockAmt, AmuletOrb paramOrb) {
    }

    @Override
    public void onOtherCardPlayed(AbstractCard c, AmuletOrb paramOrb) {
    }

    @Override
    public int onHeal(int healAmount, AmuletOrb paramOrb) {
        return healAmount;
    }

    @Override
    public int returnCountDown() {
        return 1;
    }
}


