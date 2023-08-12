package shadowverse.cards.Necromancer.LastWords;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import shadowverse.cards.Neutral.Temp.AbyssalColonel_Crystalize;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;


public class AbyssalColonel
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:AbyssalColonel";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:AbyssalColonel");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/AbyssalColonel.png";

    public AbyssalColonel() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, 1, CardType.POWER);
        this.baseDamage = 36;
        this.baseBlock = 18;
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        this.tags.add(AbstractShadowversePlayer.Enums.CONDEMNED);
        this.cardsToPreview = new AbyssalColonel_Crystalize();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(6);
            upgradeBlock(3);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 3));
        AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
        addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new AbyssalColonel();
    }
}

