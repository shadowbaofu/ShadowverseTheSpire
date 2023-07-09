package shadowverse.cards.Dragon.Ramp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.characters.Dragon;
import shadowverse.powers.Jerva2Power;
import shadowverse.powers.JervaPower;
import shadowverse.powers.VengeanceHealthPower;
import shadowverse.stance.Vengeance;

import java.util.ArrayList;
import java.util.List;

public class Jerva extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Jerva";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Jerva");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Jerva2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Jerva.png";
    public static final String IMG_PATH2 = "img/cards/Jerva2.png";

    public Jerva() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.POWER, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.SELF);
        this.isEthereal = true;
    }



    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Jerva"));
                int half = AbstractDungeon.player.maxHealth/2;
                int current = AbstractDungeon.player.currentHealth;
                if (!AbstractDungeon.player.hasPower(VengeanceHealthPower.POWER_ID)&&current>half){
                    this.superFlash();
                    addToBot(new WaitAction(1.2F));
                    AbstractDungeon.player.currentHealth = half;
                    AbstractDungeon.player.update();
                    addToBot(new ChangeStanceAction(new Vengeance()));
                };
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new JervaPower(AbstractDungeon.player,half,current)));
                break;
            case 1:
                addToBot(new SFXAction("Jerva2"));
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new Jerva2Power(abstractPlayer)));
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Jerva();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Jerva.this.timesUpgraded;
                Jerva.this.upgraded = true;
                Jerva.this.name = NAME + "+";
                Jerva.this.initializeTitle();
                Jerva.this.isEthereal = false;
                Jerva.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                Jerva.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Jerva.this.timesUpgraded;
                Jerva.this.textureImg = IMG_PATH2;
                Jerva.this.loadCardImage(IMG_PATH2);
                Jerva.this.name = cardStrings2.NAME;
                Jerva.this.upgradeBaseCost(2);
                Jerva.this.initializeTitle();
                Jerva.this.rawDescription = cardStrings2.DESCRIPTION;
                Jerva.this.initializeDescription();
                Jerva.this.upgraded = true;
            }
        });
        return list;
    }
}

