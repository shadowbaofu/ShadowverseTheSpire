package shadowverse.cards.Neutral.Neutral;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.ArrayList;
import java.util.List;

public class Grimnir extends AbstractNeutralCard implements BranchableUpgradeCard {
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Grimnir");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Grimnir2");
    public static final String ID = "shadowverse:Grimnir";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Grimnir.png";
    public static final String IMG_PATH2 = "img/cards/Grimnir2.png";
    private boolean branch2;
    public Grimnir() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 8;
    }

    @Override
    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("Grimnir"));
                addToBot( new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                int count = 0;
                for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                    if (c instanceof EvolutionPoint)
                        count++;
                }
                if (count > 4){
                    for (int i = 0; i < this.magicNumber-1; i++) {
                        addToBot( new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                    }
                }
                break;
            case 1:
                addToBot(new SFXAction("Grimnir2"));
                addToBot( new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot( new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                int count2 = 0;
                for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                    if (c instanceof EvolutionPoint)
                        count2++;
                }
                if (count2 > 6){
                    addToBot( new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                    addToBot( new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
                break;
        }
    }

    public void applyPowers() {
        if (branch2){
            int count = 0;
            int realBaseDamage = this.baseDamage;
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if (c instanceof EvolutionPoint)
                    count++;
            }
            if (count > 6){
                this.baseDamage += 12;
            }
            super.applyPowers();
            this.baseDamage = realBaseDamage;
            this.isDamageModified = (this.damage != this.baseDamage);
        }else {
            super.applyPowers();
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        if (branch2){
            int count = 0;
            int realBaseDamage = this.baseDamage;
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if (c instanceof EvolutionPoint)
                    count++;
            }
            if (count > 6){
                this.baseDamage += 12;
            }
            super.calculateCardDamage(mo);
            this.baseDamage = realBaseDamage;
            this.isDamageModified = (this.damage != this.baseDamage);
        }else {
            super.calculateCardDamage(mo);
        }
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Grimnir.this.timesUpgraded;
                Grimnir.this.upgraded = true;
                Grimnir.this.name = NAME + "+";
                Grimnir.this.initializeTitle();
                Grimnir.this.baseDamage = 11;
                Grimnir.this.upgradedDamage = true;

            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Grimnir.this.timesUpgraded;
                Grimnir.this.upgraded = true;
                Grimnir.this.textureImg = IMG_PATH2;
                Grimnir.this.loadCardImage(IMG_PATH2);
                Grimnir.this.name = cardStrings2.NAME;
                Grimnir.this.initializeTitle();
                Grimnir.this.rawDescription = cardStrings2.DESCRIPTION;
                Grimnir.this.initializeDescription();
                Grimnir.this.rarity = CardRarity.RARE;
                Grimnir.this.setDisplayRarity(Grimnir.this.rarity);
                Grimnir.this.branch2 = true;
            }
        });
        return list;
    }
    
    public AbstractCard makeCopy() {
        return new Grimnir();
    }
}
