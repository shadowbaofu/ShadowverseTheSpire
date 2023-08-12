package shadowverse.cards.Witch.Earth1;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.cards.Neutral.Temp.VeridicRitual;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.EarthEssence;
import shadowverse.powers.NextTurnEarthEssence;

public class OrichalcumGolem
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:OrichalcumGolem";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:OrichalcumGolem");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/OrichalcumGolem.png";

    public OrichalcumGolem() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.SELF, 0, CardType.SKILL);
        this.baseBlock = 18;
        this.tags.add(AbstractShadowversePlayer.Enums.EARTH_RITE);
        this.baseDamage = 7;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new VeridicRitual();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
            this.cardsToPreview.upgrade();
            upgradeDamage(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void rand(int[] l, int n, int m) {
        int i;
        for (i = 0; i < n - 1; i++) {
            l[i] = AbstractDungeon.cardRandomRng.random(2 * m / (n - i));
            m -= l[i];
        }
        l[i] = m;
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new NextTurnEarthEssence(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
        boolean powerExists = p.hasPower(EarthEssence.POWER_ID);
        AbstractPower earthEssence = p.getPower(EarthEssence.POWER_ID);
        if (powerExists) {
            if (p instanceof AbstractShadowversePlayer) {
                ((AbstractShadowversePlayer) p).earthCount += earthEssence.amount;
            }
            int[] l = new int[3];
            rand(l, 3, earthEssence.amount);
            int x = l[0];
            int y = l[1];
            int z = l[2];
            if (this.upgraded) {
                addToBot(new GainBlockAction(p, p, x * 8));
            } else {
                addToBot(new GainBlockAction(p, p, x * 6));
            }
            addToBot(new MakeTempCardInHandAction(c, y));
            for (int i = 0; i < z; i++) {
                addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.LIGHTNING));
            }
            addToBot(new ApplyPowerAction(p, p, new EarthEssence(p, -earthEssence.amount), -earthEssence.amount));
        }
    }

    public AbstractCard makeCopy() {
        return new OrichalcumGolem();
    }
}

