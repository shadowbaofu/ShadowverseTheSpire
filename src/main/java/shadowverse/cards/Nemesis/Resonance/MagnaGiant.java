package shadowverse.cards.Nemesis.Resonance;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.SweepingBeamEffect;
import shadowverse.Shadowverse;
import shadowverse.action.DrawPileToHandAction_Tag;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;


public class MagnaGiant extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:MagnaGiant";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:MagnaGiant");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/MagnaGiant.png";

    public MagnaGiant() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.ALL, 0, CardType.SKILL);
        this.baseBlock = 20;
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(5);
            upgradeMagicNumber(1);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && c.type != CardType.SKILL) {
                count++;
            }
        }
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void update() {
        if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                Shadowverse.Accelerate(this)) {
            setCostForTurn(0);
            this.type = CardType.SKILL;
        } else {
            if (this.type == CardType.SKILL) {
                setCostForTurn(3);
                this.type = CardType.ATTACK;
            }
        }
        super.update();
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("MagnaGiant"));
        addToBot(new GainBlockAction(p, this.block));
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && c.type != CardType.SKILL) {
                count++;
            }
        }
        addToBot(new VFXAction(p, new SweepingBeamEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.flipHorizontal), 0.4F));
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.magicNumber * count, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("MagnaGiant_Acc"));
        addToBot(new DrawPileToHandAction_Tag(1, AbstractShadowversePlayer.Enums.MACHINE, null));
    }


    public AbstractCard makeCopy() {
        return new MagnaGiant();
    }
}
