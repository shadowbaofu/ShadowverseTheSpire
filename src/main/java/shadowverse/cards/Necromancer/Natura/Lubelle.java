package shadowverse.cards.Necromancer.Natura;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.NecromanceAction;
import shadowverse.cards.Neutral.Temp.NaterranGreatTree;
import shadowverse.cards.Neutral.Temp.NecroAnimals;
import shadowverse.cards.Neutral.Temp.RebornAnimals;
import shadowverse.characters.Necromancer;
import shadowverse.powers.Cemetery;
import shadowverse.powers.LubellePower;

import java.util.ArrayList;
import java.util.List;


public class Lubelle
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Lubelle";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Lubelle");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Lubelle2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Lubelle.png";

    public Lubelle() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.POWER, Necromancer.Enums.COLOR_PURPLE, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.isEthereal = true;
        this.cardsToPreview = new NecroAnimals();
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Lubelle"));
                addToBot(new MakeTempCardInHandAction(new NecroAnimals(), this.magicNumber));
                addToBot(new MakeTempCardInHandAction(new NaterranGreatTree(), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(abstractPlayer.hb.cX, abstractPlayer.hb.cY)));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new LubellePower(abstractPlayer, this.magicNumber), this.magicNumber));
                break;
            case 1:
                addToBot(new SFXAction("Lubelle2"));
                addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.POISON));
                if (abstractPlayer.hasPower(Cemetery.POWER_ID)) {
                    if (abstractPlayer.getPower(Cemetery.POWER_ID).amount >= 20) {
                        addToBot(new NecromanceAction(20, null, new AbstractGameAction() {
                            @Override
                            public void update() {
                                AbstractCard animals = Lubelle.this.cardsToPreview.makeStatEquivalentCopy();
                                animals.baseDamage += animals.baseDamage;
                                animals.applyPowers();
                                addToBot(new MakeTempCardInHandAction(animals,4));
                                this.isDone = true;
                            }
                        }));
                    } else if (abstractPlayer.getPower(Cemetery.POWER_ID).amount >= 10) {
                        addToBot(new NecromanceAction(10, null, new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 3)));
                    } else {
                        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                    }
                }else {
                    addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return new Lubelle();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Lubelle.this.timesUpgraded;
                Lubelle.this.upgraded = true;
                Lubelle.this.name = cardStrings.NAME + "+";
                Lubelle.this.initializeTitle();
                Lubelle.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                initializeDescription();
                Lubelle.this.isEthereal = false;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Lubelle.this.timesUpgraded;
                Lubelle.this.upgraded = true;
                Lubelle.this.name = cardStrings2.NAME;
                Lubelle.this.initializeTitle();
                Lubelle.this.baseDamage = 15;
                Lubelle.this.upgradedDamage = true;
                Lubelle.this.type = CardType.ATTACK;
                Lubelle.this.target = CardTarget.ENEMY;
                Lubelle.this.rawDescription = cardStrings2.DESCRIPTION;
                Lubelle.this.initializeDescription();
                Lubelle.this.cardsToPreview = new RebornAnimals();
            }
        });
        return list;
    }
}
