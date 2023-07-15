package shadowverse.cards.Royal.Minion;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.RemoveAllOrbsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import shadowverse.action.MinionSummonAction;
import shadowverse.action.RemoveMinionAction;
import shadowverse.characters.Royal;
import shadowverse.orbs.LvbuOrb;
import shadowverse.orbs.Minion;
import shadowverse.powers.HeavenlyAegisPower;

public class Lvbu
        extends CustomCard {
    public static final String ID = "shadowverse:Lvbu";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Lvbu");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Lvbu.png";


    public Lvbu() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded){
            upgradeName();
            upgradeBaseCost(1);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Lvbu"));
        int atk = 1;
        int def = 1;
        for (AbstractOrb o : p.orbs){
            if (o instanceof Minion){
                atk += ((Minion) o).attack;
                def += ((Minion) o).attack;
            }
        }
        addToBot(new RemoveAllOrbsAction());
        boolean musou = atk + def - 2 >= 20;
        addToBot(new MinionSummonAction(new LvbuOrb(atk,def,this.makeStatEquivalentCopy(),musou)));
        if (musou){
            addToBot(new ApplyPowerAction(p,p,new HeavenlyAegisPower(p)));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Lvbu();
    }

}


