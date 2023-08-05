package shadowverse.cards.Dragon.Default;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import shadowverse.Shadowverse;
import shadowverse.cards.Neutral.Temp.Antemaria_Token;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;
import shadowverse.relics.Enkia;


public class Antemaria extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Antemaria";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Antemaria");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Antemaria.png";

    public Antemaria() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.ENEMY, 0, CardType.SKILL);
        this.baseDamage = 32;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.CONDEMNED);
        this.cardsToPreview = new Antemaria_Token();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
            upgradeDamage(10);
            this.cardsToPreview.upgrade();
        }
    }


    public void triggerOnGlowCheck() {
        if (EnergyPanel.getCurrentEnergy() < baseCost && this.type == accType) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            if ((!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && (AbstractDungeon.actionManager.cardsPlayedThisCombat
                    .get(AbstractDungeon.actionManager.cardsPlayedThisCombat
                            .size() - 1)).hasTag(AbstractShadowversePlayer.Enums.CONDEMNED)) || AbstractDungeon.player.hasRelic(Enkia.ID)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            } else {
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
        }
    }

    @Override
    public void baseUse(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Antemaria"));
        addToBot(new VFXAction(new ViolentAttackEffect(abstractMonster.hb.cX, abstractMonster.hb.cY, Color.ORANGE), 0.8F));
        if ((AbstractDungeon.actionManager.cardsPlayedThisCombat.size() > 1 && (AbstractDungeon.actionManager.cardsPlayedThisCombat
                .get(AbstractDungeon.actionManager.cardsPlayedThisCombat
                        .size() - 2)).hasTag(AbstractShadowversePlayer.Enums.CONDEMNED)) || AbstractDungeon.player.hasRelic(Enkia.ID)) {
            addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage * 2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        } else {
            addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void accUse(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Antemaria_Acc"));
        addToBot(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Antemaria();
    }
}

