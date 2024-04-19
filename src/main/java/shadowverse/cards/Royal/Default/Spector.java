package shadowverse.cards.Royal.Default;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;
import shadowverse.powers.NextTurnV;
import shadowverse.powers.NextTurnV2;

import java.util.ArrayList;
import java.util.List;

public class Spector extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Spector";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Spector");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Spector2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Spector.png";
    public int baseCost = 2;
    public int accCost = 0;
    public CardType baseType = CardType.ATTACK;
    public CardType accType = CardType.SKILL;
    public boolean played;
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new V());
        list.add(new DesperadosShot());
        return list;
    }

    public Spector() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 25;
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        this.exhaust = true;
    }

    @Override
    public void update() {
        super.update();
        if (chosenBranch() == 0){
            if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                if (this.type == baseType) {
                    this.baseCost = this.costForTurn;
                } else {
                    if (this.cost <= this.accCost){
                        this.baseCost = this.cost;
                    }
                }
                if (!played){
                    if (EnergyPanel.getCurrentEnergy() < baseCost) {
                        setCostForTurn(accCost);
                        this.type = accType;
                    }else {
                        if (this.type==accType){
                            setCostForTurn(baseCost);
                            this.type = baseType;
                        }
                    }
                }
            }else {
                resetAttributes();
                this.type = baseType;
            }
        }
        if (this.hb.hovered) {
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 2.0F;
                this.cardsToPreview = returnProphecy().get(previewIndex).makeCopy();
                if (this.previewIndex == returnProphecy().size() - 1) {
                    this.previewIndex = 0;
                } else {
                    this.previewIndex++;
                }
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        played = false;
    }

    public void triggerOnGlowCheck() {
        if (EnergyPanel.getCurrentEnergy() < baseCost && this.type == accType) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (chosenBranch()){
            case 0 :
                if (this.type==accType && this.costForTurn == accCost){
                    accUse(p,m);
                }else {
                    baseUse(p,m);
                }
                played = true;
                break;
            case 1:
                addToBot(new SFXAction("Spector2"));
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                addToBot(new ApplyPowerAction(p, p, new NextTurnV2(p, 1)));
                addToBot(new MakeTempCardInHandAction(new RevolutionShot()));
                for (AbstractCard c : p.hand.group){
                    if (c instanceof EvolutionPoint){
                        addToBot(new MakeTempCardInHandAction(new RevolutionShot()));
                        break;
                    }
                }
                break;
        }
    }

    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new NextTurnV(p, 1)));
    }


    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new DesperadosShot()));
    }


    @Override
    public AbstractCard makeCopy() {
        return new Spector();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Spector.this.timesUpgraded;
                Spector.this.upgraded = true;
                Spector.this.name = cardStrings.NAME + "+";
                Spector.this.initializeTitle();
                Spector.this.baseDamage = 35;
                Spector.this.upgradedDamage = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Spector.this.timesUpgraded;
                Spector.this.upgraded = true;
                Spector.this.name = cardStrings2.NAME;
                Spector.this.initializeTitle();
                Spector.this.rawDescription = cardStrings2.DESCRIPTION;
                Spector.this.initializeDescription();
            }
        });
        return list;
    }
}

