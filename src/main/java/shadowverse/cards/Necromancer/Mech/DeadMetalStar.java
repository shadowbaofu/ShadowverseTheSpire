package shadowverse.cards.Necromancer.Mech;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import shadowverse.action.ReanimateAction;
import shadowverse.cards.Necromancer.Burial.DemonicProcession;
import shadowverse.cards.Necromancer.Burial.SpiritCurator;
import shadowverse.cards.Necromancer.Default.HungrySlash;
import shadowverse.cards.Necromancer.Ghosts.Ferry;
import shadowverse.cards.Necromancer.Burial.TheLovers;
import shadowverse.cards.Neutral.Temp.InstantPotion;
import shadowverse.cards.Neutral.Temp.ProductMachine;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;


public class DeadMetalStar
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:DeadMetalStar";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DeadMetalStar");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DeadMetalStar.png";

    public DeadMetalStar() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.RARE, CardTarget.ENEMY, 1, CardType.SKILL);
        this.baseDamage = 25;
        this.baseBlock = 25;
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.cardsToPreview = new ProductMachine();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
            upgradeBlock(5);
        }
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c instanceof DemonicProcession || c instanceof TheLovers || c instanceof HungrySlash || c instanceof SpiritCurator || c instanceof Ferry || c instanceof InstantPotion) {
            this.type = CardType.ATTACK;
            this.resetAttributes();
        }
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new ReanimateAction(3));
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        addToBot(new SFXAction("DeadMetalStar"));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("DeadMetalStar_Acc"));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 3));
    }


    public AbstractCard makeCopy() {
        return new DeadMetalStar();
    }
}

