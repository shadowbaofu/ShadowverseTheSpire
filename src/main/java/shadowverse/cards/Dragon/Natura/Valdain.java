package shadowverse.cards.Dragon.Natura;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Temp.NaterranGreatTree;
import shadowverse.cards.Neutral.Temp.ShadowCorrosion;
import shadowverse.cards.Neutral.Temp.ShadowRejection;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;
import shadowverse.powers.NaterranTree;

import java.util.ArrayList;
import java.util.List;

public class Valdain extends AbstractRightClickCard2 implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Valdain";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Valdain");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Valdain2");
    public static CardStrings cardStrings3 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Valdain3");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Valdain.png";
    public static final String IMG_PATH2 = "img/cards/Valdain2.png";
    private boolean hasFusion = false;
    private boolean triggered;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private int x;
    private int y;
    private int z;

    public Valdain() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 14;
        this.cardsToPreview = new ShadowCorrosion();
        this.tags.add(AbstractShadowversePlayer.Enums.NATURAL);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (chosenBranch() == 1) {
            this.rawDescription = "";
            if (x > 0) {
                this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[0];
            }
            if (y > 0) {
                this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[1];
            }
            if (z > 0) {
                this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[2];
            }
            if (x + y + z == 0) {
                this.rawDescription = cardStrings2.DESCRIPTION;
            } else {
                this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[3];
            }
            this.initializeDescription();
        } else if (chosenBranch() == 2) {
            if (AbstractDungeon.player instanceof AbstractShadowversePlayer) {
                int count = ((AbstractShadowversePlayer) AbstractDungeon.player).naterranCount;
                this.rawDescription = cardStrings3.DESCRIPTION;
                this.rawDescription += cardStrings3.EXTENDED_DESCRIPTION[0] + count;
                this.rawDescription += cardStrings3.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            }
        }
    }

    @Override
    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Valdain"));
                addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                break;
            case 1:
                addToBot(new SFXAction("Valdain2"));
                addToBot(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
                if (abstractPlayer.hasPower(NaterranTree.POWER_ID)) {
                    addToBot(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
                }
                int dmg = this.damage;
                if (x > 0) {
                    dmg = this.damage * 2;
                }
                if (y > 0) {
                    addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new StrengthPower(abstractPlayer, 1), 1));
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DexterityPower(abstractPlayer, 1), 1));
                }
                if (z > 0) {
                    addToBot(new HealAction(abstractPlayer, abstractPlayer, 5));
                }
                addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, dmg, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                addToBot(new SFXAction("Valdain3"));
                addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                if (!triggered) {
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        if (mo != null && !mo.isDeadOrEscaped() && !mo.isDying && !mo.halfDead) {
                            addToBot(new ApplyPowerAction(mo, abstractPlayer, new StrengthPower(mo, -2), -2, true, AbstractGameAction.AttackEffect.NONE));
                            addToBot(new ApplyPowerAction(mo, abstractPlayer, new DexterityPower(mo, -2), -2, true, AbstractGameAction.AttackEffect.NONE));
                        }
                    }
                    triggered = true;
                }
                if (AbstractDungeon.player instanceof AbstractShadowversePlayer) {
                    if (((AbstractShadowversePlayer) AbstractDungeon.player).naterranCount > 9)
                        addToBot(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer, this.damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
                }
                break;
        }
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Valdain.this.timesUpgraded;
                Valdain.this.upgraded = true;
                Valdain.this.name = cardStrings.NAME + "+";
                Valdain.this.initializeTitle();
                Valdain.this.upgradeName();
                Valdain.this.baseBlock = 18;
                Valdain.this.upgradedBlock = true;
                Valdain.this.cardsToPreview.upgrade();
                Valdain.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                Valdain.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Valdain.this.timesUpgraded;
                Valdain.this.upgraded = true;
                Valdain.this.textureImg = IMG_PATH2;
                Valdain.this.loadCardImage(IMG_PATH2);
                Valdain.this.name = cardStrings2.NAME;
                Valdain.this.initializeTitle();
                Valdain.this.rawDescription = cardStrings2.DESCRIPTION;
                Valdain.this.initializeDescription();
                Valdain.this.baseDamage = 15;
                Valdain.this.upgradedDamage = true;
                Valdain.this.baseMagicNumber = 10;
                Valdain.this.magicNumber = Valdain.this.baseMagicNumber;
                Valdain.this.upgradedMagicNumber = true;
                Valdain.this.cardsToPreview = null;
                Valdain.this.target = CardTarget.ENEMY;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Valdain.this.timesUpgraded;
                Valdain.this.upgraded = true;
                Valdain.this.name = cardStrings3.NAME;
                Valdain.this.initializeTitle();
                Valdain.this.rawDescription = cardStrings3.DESCRIPTION;
                Valdain.this.initializeDescription();
                Valdain.this.baseDamage = 15;
                Valdain.this.upgradedDamage = true;
                Valdain.this.cardsToPreview = new ShadowRejection();
                Valdain.this.target = CardTarget.ENEMY;
            }
        });
        return list;
    }

    public void atTurnStart() {
        hasFusion = false;
    }

    @Override
    protected void onRightClick() {
        if (this.chosenBranch() == 1) {
            if (!this.hasFusion && AbstractDungeon.player != null) {
                addToBot(new SelectCardsInHandAction(8, TEXT[0], true, true, card -> {
                    return card instanceof NaterranGreatTree;
                }, abstractCards -> {
                    if (abstractCards.size() > 0) {
                        this.hasFusion = true;
                        if (abstractCards.size() >= 3) {
                            x = 1;
                            y = 1;
                            z = 1;
                        } else if (abstractCards.size() == 2) {
                            x = y = z = 1;
                            int rnd = AbstractDungeon.cardRandomRng.random(2);
                            if (rnd == 0) {
                                x = 0;
                            } else if (rnd == 1) {
                                y = 0;
                            } else {
                                z = 0;
                            }
                        } else {
                            int rnd = AbstractDungeon.cardRandomRng.random(2);
                            if (rnd == 0) {
                                x = 1;
                            } else if (rnd == 1) {
                                y = 1;
                            } else {
                                z = 1;
                            }
                        }
                    }
                    for (AbstractCard c : abstractCards) {
                        addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                    }
                }));
            }
        }
    }
}
