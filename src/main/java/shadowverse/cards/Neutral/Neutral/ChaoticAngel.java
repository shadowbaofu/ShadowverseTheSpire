package shadowverse.cards.Neutral.Neutral;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowverse.Shadowverse;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.powers.HeavenlyAegisPower;

import java.util.ArrayList;

public class ChaoticAngel
        extends AbstractNeutralCard {
    public static final String ID = "shadowverse:Aerin";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Aerin");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Aerin.png";
    private ArrayList<String> dup = new ArrayList<>();
    public int enhanceCost;
    public int baseCost;
    public boolean exFreeOnce;
    public int exCost;
    public int exCostForTurn;

    public int ex;
    public ChaoticAngel() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 0;
        this.baseMagicNumber = 7;
        this.magicNumber = this.baseMagicNumber;
        this.baseCost = cost;
        this.enhanceCost = 4;
        this.exCost = cost;
        this.exCostForTurn = cost;
        this.exFreeOnce = false;
        this.ex = 0;
        this.cardsToPreview = new WingedInversion();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            if (Shadowverse.Enhance(enhanceCost)) {
                if (this.ex == 0) {
                    this.exCost = this.cost;
                    this.exCostForTurn = this.costForTurn;
                }
                this.ex = 1;
                this.exFreeOnce = this.freeToPlayOnce;
                setCostForTurn(enhanceCost);
            } else {
                if (this.ex > 0) {
                    setCostForTurn(enhanceCost);
                    this.cost = this.exCost;
                    this.costForTurn = this.exCostForTurn;
                    this.freeToPlayOnce = this.exFreeOnce;
                }
                this.ex = 0;
            }
        }
    }

    public void applyPowers() {
        for (AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisCombat){
            if (c.color == CardColor.COLORLESS &&!dup.contains(c.cardID)){
                dup.add(c.cardID);
            }
        }
        int amount = dup.size();
        int realBaseDamage = this.baseDamage;
        this.baseDamage += amount * this.magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        for (AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisCombat){
            if (c.color == CardColor.COLORLESS &&!dup.contains(c.cardID)){
                dup.add(c.cardID);
            }
        }
        int amount = dup.size();
        int realBaseDamage = this.baseDamage;
        this.baseDamage += amount * this.magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("ChaoticAngel"));
        calculateCardDamage(abstractMonster);
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (this.costForTurn == 4) {
            addToBot(new GainEnergyAction(2));
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HeavenlyAegisPower(abstractPlayer)));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for (int i = 0; i < 2; i++) {
                        AbstractCard tmp = cardsToPreview.makeStatEquivalentCopy();
                        tmp.exhaustOnUseOnce = true;
                        tmp.exhaust = true;
                        tmp.rawDescription += " NL " + TEXT + " 。";
                        tmp.initializeDescription();
                        tmp.applyPowers();
                        tmp.lighten(true);
                        tmp.setAngle(0.0F);
                        tmp.drawScale = 0.12F;
                        tmp.targetDrawScale = 0.75F;
                        tmp.current_x = Settings.WIDTH / 2.0F;
                        tmp.current_y = Settings.HEIGHT / 2.0F;
                        abstractPlayer.hand.addToTop(tmp);
                    }
                    abstractPlayer.hand.refreshHandLayout();
                    abstractPlayer.hand.applyPowers();
                    this.isDone = true;
                }
            });
        }
    }


    public AbstractCard makeCopy() {
        return new ChaoticAngel();
    }
}


