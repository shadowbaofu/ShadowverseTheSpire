package shadowverse.cards.Nemesis.Default;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import shadowverse.characters.Nemesis;
import shadowverse.stance.Resonance;


public class SyntheticEden extends CustomCard {
    public static final String ID = "shadowverse:SyntheticEden";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SyntheticEden");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SyntheticEden.png";

    public SyntheticEden() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.ENEMY);
        this.baseBlock = 20;
        this.baseDamage = 35;
        this.isMultiDamage = true;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeDamage(6);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("SyntheticEden"));
        addToBot(new GainBlockAction(p, p, this.block));
        if (p.stance.ID.equals(Resonance.STANCE_ID)) {
            addToBot(new DrawCardAction(4, new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
                    tickDuration();
                    if (this.isDone)
                        for (AbstractCard c : DrawCardAction.drawnCards) {
                            if (c.costForTurn > 0) {
                                c.costForTurn = c.costForTurn - 1;
                                c.isCostModifiedForTurn = true;
                            }
                        }
                }
            }));
        } else {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                addToBot(new VFXAction(new WeightyImpactEffect(mo.hb.cX, mo.hb.cY)));
            }
            addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new SyntheticEden();
    }
}

