package shadowverse.cards.Royal.Rally;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import shadowverse.action.MinionSummonAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Royal;
import shadowverse.orbs.*;

public class FrenziedCorpsmaster extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:FrenziedCorpsmaster";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/FrenziedCorpsmaster.png";
    public static final int ACCELERATE = 1;

    public FrenziedCorpsmaster() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, 1, CardType.SKILL);
        this.baseBlock = 8;
        this.baseDamage = 0;
        this.isMultiDamage = true;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
        }
    }

    public int rally() {
        int rally = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof Minion && !(o instanceof AmbushMinion) && !(o instanceof ErikaOrb)) {
                rally++;
            }
        }

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.ATTACK && !(c.hasTag(CardTags.STRIKE))) {
                rally++;
            }
        }
        return rally;
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToBot(new GainBlockAction(p, this.block));
        this.baseDamage = rally() * 2;
        this.calculateCardDamage(null);
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "") + "_Acc"));
        AbstractDungeon.actionManager.addToBottom(new MinionSummonAction(new SteelcladKnight()));
        AbstractDungeon.actionManager.addToBottom(new MinionSummonAction(new HeavyKnight()));
    }


    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseDamage = rally() * 2;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + this.baseDamage + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + this.baseDamage + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new FrenziedCorpsmaster();
    }
}

