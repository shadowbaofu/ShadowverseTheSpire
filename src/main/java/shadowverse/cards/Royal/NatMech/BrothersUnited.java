package shadowverse.cards.Royal.NatMech;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.NaterranGreatTree;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;
import shadowverse.orbs.*;

import java.util.ArrayList;
import java.util.List;

public class BrothersUnited extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:BrothersUnited";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/BrothersUnited.png";
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:BrothersUnited2");
    private int previewBranch;

    public BrothersUnited() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Royal.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 4;
        this.cardsToPreview = new NaterranGreatTree();
        this.tags.add(AbstractShadowversePlayer.Enums.NATURAL);
    }
    

    public int rally() {
        int rally = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof Minion && !(o instanceof AmbushMinion) && !(o instanceof ErikaOrb)) {
                rally++;
            }
        }

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.ATTACK && !(c.hasTag(CardTags.STRIKE))) {
                rally++;
            }
        }
        return rally;
    }

    @Override
    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.previewBranch == 0){
            addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        }else {
            addToBot(new SFXAction("BrothersUnited2"));
        }
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
        addToBot(new MakeTempCardInHandAction(c, 1));
    }


    @Override
    public void applyPowers() {
        if (this.upgraded) {
            this.baseDamage = 10;
        } else {
            this.baseDamage = 8;
        }
        if (this.previewBranch == 0){
            if (rally() >= 7) {
                this.baseDamage += this.magicNumber;
            }
            super.applyPowers();
            this.rawDescription = cardStrings.DESCRIPTION;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + rally() + cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }else {
            if (rally() >= 10) {
                this.baseDamage += this.magicNumber;
            }
            super.applyPowers();
            this.rawDescription = cardStrings2.DESCRIPTION;
            this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[0] + rally() + cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + rally() + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new BrothersUnited();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++BrothersUnited.this.timesUpgraded;
                BrothersUnited.this.upgraded = true;
                BrothersUnited.this.name = cardStrings.NAME + "+";
                BrothersUnited.this.initializeTitle();
                BrothersUnited.this.baseMagicNumber = 5;
                BrothersUnited.this.magicNumber = BrothersUnited.this.baseMagicNumber;
                BrothersUnited.this.upgradedMagicNumber = true;
                BrothersUnited.this.previewBranch = 0;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++BrothersUnited.this.timesUpgraded;
                BrothersUnited.this.upgraded = true;
                BrothersUnited.this.name = cardStrings2.NAME;
                BrothersUnited.this.initializeTitle();
                BrothersUnited.this.baseMagicNumber = 10;
                BrothersUnited.this.magicNumber = BrothersUnited.this.baseMagicNumber;
                BrothersUnited.this.upgradedMagicNumber = true;
                BrothersUnited.this.rawDescription = cardStrings2.DESCRIPTION;
                BrothersUnited.this.initializeDescription();
                BrothersUnited.this.previewBranch = 1;
            }
        });
        return list;
    }
}

