package shadowverse.cards.Witch.Chess;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Temp.MagicalPawn;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;


public class Battery extends CustomCard {
    public static final String ID = "shadowverse:Battery";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Battery");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Battery.png";

    public Battery() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 6;
        this.cardsToPreview = new MagicalPawn();
        this.tags.add(AbstractShadowversePlayer.Enums.CHESS);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        for (AbstractCard c : abstractPlayer.hand.group){
            if (c instanceof MagicalRook){
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                break;
            }
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Battery();
    }
}

