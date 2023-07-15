package shadowverse.cards.Neutral.Temp;



import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import shadowverse.characters.Nemesis;


public class Shin_Token extends CustomCard {
    public static final String ID = "shadowverse:Shin_Token";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Shin_Token.png";

    public Shin_Token() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseBlock = 4;
        this.baseDamage = 12;
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeBlock(2);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Shin_Token"));
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (AbstractDungeon.actionManager.turn > 4){
            addToBot(new DamageAction(m,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            addToBot(new GainBlockAction(abstractPlayer,this.block));
            addToBot(new ApplyPowerAction(m,abstractPlayer,new VulnerablePower(m,2,false),2));
            for (AbstractCard c : abstractPlayer.hand.group){
                addToBot(new ReduceCostForTurnAction(c,1));
            }
        }else {
            int rng = AbstractDungeon.cardRng.random(2);
            switch (rng){
                case 0:
                    addToBot(new DamageAction(m,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                    addToBot(new GainBlockAction(abstractPlayer,this.block));
                    break;
                case 1:
                    addToBot(new ApplyPowerAction(m,abstractPlayer,new VulnerablePower(m,2,false),2));
                    break;
                case 2:
                    for (AbstractCard c : abstractPlayer.hand.group){
                        addToBot(new ReduceCostForTurnAction(c,1));
                    }
                    break;
            }
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Shin_Token();
    }
}

