package shadowverse.cards.Elf.NatMech;


import basemod.abstracts.CustomCard;
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
import com.megacrit.cardcrawl.powers.*;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.BackToNature;
import shadowverse.cards.Neutral.Temp.Fairy;
import shadowverse.cards.Neutral.Temp.LadicaEmbrace;
import shadowverse.cards.Neutral.Temp.Packing;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Elf;

import java.util.ArrayList;
import java.util.List;


public class Ladica extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Ladica";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Ladica");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Ladica2");
    public static CardStrings cardStrings3 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Ladica3");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Ladica.png";
    public static final String IMG_PATH2 = "img/cards/Ladica2.png";
    public static final String IMG_PATH3 = "img/cards/Ladica3.png";
    private int branchPreview = 0;
    private int damageNum = 1;
    private boolean doubleDamage = false;
    private static AbstractCard packing = new Packing();
    private static AbstractCard embrace = new LadicaEmbrace();
    private static AbstractCard back = new BackToNature();

    public Ladica() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 12;
        this.tags.add(AbstractShadowversePlayer.Enums.NATURAL);
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void update() {
        super.update();
        if (this.branchPreview == 1) {
            this.cardsToPreview = embrace;
        } else if (this.branchPreview == 0) {
            this.cardsToPreview = packing;
        } else {
            this.cardsToPreview = back;
        }
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                boolean fairyCheck = false;
                addToBot(new SFXAction("Ladica"));
                addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
                for (AbstractCard ca : abstractPlayer.hand.group) {
                    if (ca instanceof Fairy) {
                        fairyCheck = true;
                        break;
                    }
                }
                if (fairyCheck) {
                    addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH, true));
                }
                if (abstractPlayer.hasPower("shadowverse:NaterranTree")) {
                    addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
                }
                break;
            case 1:
                addToBot(new SFXAction("Ladica2"));
                for (int i = 0; i < this.damageNum; i++) {
                    if (doubleDamage) {
                        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage * 2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
                    } else {
                        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
                    }
                }
                this.damageNum = 1;
                this.doubleDamage = false;
                break;
            case 2:
                addToBot(new SFXAction("Ladica3"));
                addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
                break;
            default:
                break;
        }
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (chosenBranch() == 1) {
            int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
            switch (count) {
                case 1:
                    addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                    break;
                case 4:
                    addToBot(new GainEnergyAction(1));
                    break;
                case 8:
                    this.damageNum = 2;
                    break;
                case 12:
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 3), 3));
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 3), 3));
                    this.doubleDamage = true;
                    break;
                default:
                    break;
            }
        } else if (chosenBranch() == 2) {
            int count = 0;
            for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                if (c.hasTag(AbstractShadowversePlayer.Enums.NATURAL)) {
                    count++;
                }
            }
            int playedCount = count;
            if (c.hasTag(AbstractShadowversePlayer.Enums.NATURAL)) {
                playedCount++;
            }
            if (playedCount - count >= 4) {
                addToBot(new GainEnergyAction(1));
            }
            if (playedCount - count == 4) {
                addToBot(new SFXAction("Ladica3_Eff"));
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
            }
        }
    }

    public AbstractCard makeCopy() {
        return new Ladica();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Ladica.this.timesUpgraded;
                Ladica.this.upgraded = true;
                Ladica.this.name = cardStrings.NAME + "+";
                Ladica.this.initializeTitle();
                Ladica.this.baseDamage = 16;
                ;
                Ladica.this.upgradedDamage = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Ladica.this.timesUpgraded;
                Ladica.this.upgraded = true;
                Ladica.this.textureImg = IMG_PATH2;
                Ladica.this.loadCardImage(IMG_PATH2);
                Ladica.this.name = cardStrings2.NAME;
                Ladica.this.initializeTitle();
                Ladica.this.rawDescription = cardStrings2.DESCRIPTION;
                Ladica.this.initializeDescription();
                Ladica.this.branchPreview = 1;
                Ladica.this.baseDamage = 18;
                Ladica.this.upgradedDamage = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Ladica.this.timesUpgraded;
                Ladica.this.upgraded = true;
                Ladica.this.textureImg = IMG_PATH3;
                Ladica.this.loadCardImage(IMG_PATH3);
                Ladica.this.name = cardStrings3.NAME;
                Ladica.this.initializeTitle();
                Ladica.this.rawDescription = cardStrings3.DESCRIPTION;
                Ladica.this.initializeDescription();
                Ladica.this.branchPreview = 2;
            }
        });

        return list;
    }
}
