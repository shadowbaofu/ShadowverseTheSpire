package shadowverse.cards.Bishop.Default;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.AbstractAmuletCard;
import shadowverse.cards.AbstractNoCountDownAmulet;
import shadowverse.cards.Neutral.Temp.MarkOfBalance;
import shadowverse.cards.Neutral.Temp.SigilOfBalance;
import shadowverse.characters.Bishop;

import java.util.ArrayList;
import java.util.List;


public class Marlone extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Marlone";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Marlone");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Marlone2");
    public static CardStrings cardStrings3 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Marlone3");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Marlone.png";
    public static final String IMG_PATH2 = "img/cards/Marlone2.png";
    private int branch;
    private boolean triggered;

    private static AbstractCard markOfBalance = new MarkOfBalance();
    private static AbstractCard sigilOfBalance = new SigilOfBalance();

    private static AbstractCard upgradedMark(){
        AbstractCard c = new MarkOfBalance();
        c.upgrade();
        return c;
    }



    public Marlone() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 9;
    }

    public void update() {
        super.update();
        if (this.branch == 0) {
            if (this.upgraded){
                this.cardsToPreview = upgradedMark();
            }else {
                this.cardsToPreview = markOfBalance;
            }
        } else if (this.branch == 2) {
            this.cardsToPreview = sigilOfBalance;
        }
    }

    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Marlone"));
                addToBot(new GainBlockAction(p, this.block));
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                break;
            case 1:
                addToBot(new SFXAction("Marlone2"));
                int atkAmt = 0;
                for (AbstractCard c : p.hand.group) {
                    if (c.type == AbstractCard.CardType.ATTACK && c != this) {
                        atkAmt++;
                    }
                }
                int monsterAmt = 0;
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        monsterAmt++;
                        addToBot(new VFXAction(new ClashEffect(mo.hb.cX, mo.hb.cY), 0.1F));
                    }
                }
                addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix((this.damage - atkAmt * 5) * monsterAmt, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
                break;
            case 2:
                addToBot(new SFXAction("Marlone3"));
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        addToBot(new VFXAction(new ClashEffect(mo.hb.cX, mo.hb.cY), 0.1F));
                    }
                }
                addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
                for (AbstractCard c : p.hand.group) {
                    if (c.type != CardType.SKILL && !(c instanceof AbstractAmuletCard) && !(c instanceof AbstractNoCountDownAmulet)) {
                        addToBot(new ExhaustSpecificCardAction(c, p.hand));
                    }
                }
                if (!triggered) {
                    addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                    triggered = true;
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Marlone();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Marlone.this.timesUpgraded;
                Marlone.this.upgraded = true;
                Marlone.this.name = cardStrings.NAME + "+";
                Marlone.this.initializeTitle();
                Marlone.this.baseBlock = 12;
                Marlone.this.upgradedBlock = true;
                Marlone.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                Marlone.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Marlone.this.timesUpgraded;
                Marlone.this.upgraded = true;
                Marlone.this.textureImg = IMG_PATH2;
                Marlone.this.loadCardImage(IMG_PATH2);
                Marlone.this.name = cardStrings2.NAME;
                Marlone.this.baseDamage = 20;
                Marlone.this.upgradedDamage = true;
                Marlone.this.initializeTitle();
                Marlone.this.rawDescription = cardStrings2.DESCRIPTION;
                Marlone.this.initializeDescription();
                Marlone.this.branch = 1;
                Marlone.this.cardsToPreview = null;
                Marlone.this.rarity = CardRarity.RARE;
                Marlone.this.setDisplayRarity(Marlone.this.rarity);
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Marlone.this.timesUpgraded;
                Marlone.this.upgraded = true;
                Marlone.this.name = cardStrings3.NAME;
                Marlone.this.baseDamage = 13;
                Marlone.this.upgradedDamage = true;
                Marlone.this.upgradeBaseCost(2);
                Marlone.this.initializeTitle();
                Marlone.this.rawDescription = cardStrings3.DESCRIPTION;
                Marlone.this.initializeDescription();
                Marlone.this.branch = 2;
            }
        });
        return list;
    }
}

