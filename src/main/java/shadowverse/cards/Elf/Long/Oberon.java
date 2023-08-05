package shadowverse.cards.Elf.Long;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
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
import shadowverse.cards.Neutral.Temp.Oberon_Copy;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Elf;

;


public class Oberon
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Oberon";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Oberon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Oberon.png";

    public Oberon() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.RARE, CardTarget.ENEMY,1,CardType.SKILL);
        this.baseDamage = 40;
        this.cardsToPreview = new Oberon_Copy();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(10);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = AbstractDungeon.actionManager.cardsPlayedThisCombat.size();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            if (this.type == baseType) {
                this.baseCost = this.costForTurn;
            } else {
                if (this.cost <= this.accCost){
                    this.baseCost = this.cost;
                }
            }
            if (!played){
                if (EnergyPanel.getCurrentEnergy() < baseCost && AbstractDungeon.actionManager.cardsPlayedThisCombat.size()>=30) {
                    setCostForTurn(accCost);
                    this.type = accType;
                }else {
                    if (this.type==accType){
                        setCostForTurn(baseCost);
                        this.type = baseType;
                    }
                }
            }
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Oberon_Acc"));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Oberon"));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Oberon();
    }
}

