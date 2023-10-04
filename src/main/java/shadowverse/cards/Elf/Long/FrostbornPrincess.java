package shadowverse.cards.Elf.Long;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.characters.Elf;


public class FrostbornPrincess extends CustomCard{
    public static final String ID = "shadowverse:FrostbornPrincess";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:FrostbornPrincess");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/FrostbornPrincess.png";

    public FrostbornPrincess() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 4;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster m) {
        addToBot(new SFXAction("FrostbornPrincess"));
        int amount = 1;
        int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        if (count > 4) {
            amount = 3;
            addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
        } else if (count > 2) {
            amount = 2;
        }
        addToBot(new DamageAction(m, new DamageInfo(abstractPlayer, this.damage * amount, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (count > 6) {
            addToBot(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(),1,false,false,false));
            this.exhaust = true;
            abstractPlayer.hand.removeCard(this);
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new FrostbornPrincess();
    }

}

