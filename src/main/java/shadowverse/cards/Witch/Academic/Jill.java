package shadowverse.cards.Witch.Academic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import shadowverse.action.DrawPileToHandAction_Tag;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.cards.Neutral.Temp.MysterianCircle;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;


public class Jill
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Jill";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Jill");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Jill.png";


    public Jill() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.COMMON, CardTarget.ALL_ENEMY, 0, CardType.SKILL);
        this.tags.add(AbstractShadowversePlayer.Enums.MYSTERIA);
        this.baseBlock = 10;
        this.cardsToPreview = new MysterianCircle();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Jill"));
        if (p instanceof AbstractShadowversePlayer){
            ((AbstractShadowversePlayer)p).mysteriaCount++;
        }
        addToBot(new DrawPileToHandAction_Tag(2,AbstractShadowversePlayer.Enums.MYSTERIA,null));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : p.hand.group) {
                    if (c.hasTag(AbstractShadowversePlayer.Enums.MYSTERIA)) {
                        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
                        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(1, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Jill_Acc"));
        if (p instanceof AbstractShadowversePlayer){
            ((AbstractShadowversePlayer)p).mysteriaCount++;
        }
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
    }

    public AbstractCard makeCopy() {
        return new Jill();
    }
}

