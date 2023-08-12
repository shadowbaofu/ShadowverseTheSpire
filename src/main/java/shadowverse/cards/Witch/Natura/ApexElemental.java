package shadowverse.cards.Witch.Natura;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import shadowverse.cards.Neutral.Temp.NaterranGreatTree;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.orbs.AmuletOrb;

public class ApexElemental
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:ApexElemental";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ApexElemental");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ApexElemental.png";

    public ApexElemental() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.ENEMY, 0, CardType.SKILL);
        this.baseDamage = 15;
        this.cardsToPreview = new NaterranGreatTree();
        this.tags.add(AbstractShadowversePlayer.Enums.NATURAL);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }


    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        boolean powerExists = p.hasPower(NaterranGreatTree.ID);
        AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
        if (powerExists) {
            for (AbstractOrb o : p.orbs) {
                if (o instanceof AmuletOrb) {
                    if (((AmuletOrb) o).amulet instanceof NaterranGreatTree) {
                        addToBot(new EvokeSpecificOrbAction(o));
                    }
                }
            }
            addToBot(new DamageAction(m, new DamageInfo(m, 9, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            addToBot(new RemoveSpecificPowerAction(p, p, "shadowverse:NaterranTree"));
            addToBot(new MakeTempCardInHandAction(c, 1));
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, 6, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }

    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        boolean powerExists = p.hasPower(NaterranGreatTree.ID);
        AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
        if (powerExists) {
            for (AbstractOrb o : p.orbs) {
                if (o instanceof AmuletOrb) {
                    if (((AmuletOrb) o).amulet instanceof NaterranGreatTree) {
                        addToBot(new EvokeSpecificOrbAction(o));
                    }
                }
            }
            addToBot(new RemoveSpecificPowerAction(p, p, "shadowverse:NaterranTree"));
            addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage * 2, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE, true));
            addToBot(new MakeTempCardInHandAction(c, 1));
        } else {
            addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE, true));
        }
    }


    public AbstractCard makeCopy() {
        return new ApexElemental();
    }
}

