package shadowverse.cards.Neutral.Neutral;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.Shadowverse;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Neutral.Temp.Odin2_Copy;
import shadowverse.cards.Neutral.Temp.Sleipnir;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.ArrayList;
import java.util.List;

public class Odin extends AbstractNeutralCard implements BranchableUpgradeCard {
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Odin");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Odin2");
    public static final String ID = "shadowverse:Odin";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Odin.png";
    public static final String IMG_PATH2 = "img/cards/Odin2.png";
    private int previewBranch;

    public Odin() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 18;
        this.exhaust = true;
        this.cardsToPreview = new Sleipnir();
    }

    public void update() {
        if (previewBranch == 1) {
            if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                    Shadowverse.Accelerate(this)) {
                setCostForTurn(2);
                this.type = CardType.SKILL;
            } else {
                if (this.type == CardType.SKILL) {
                    setCostForTurn(4);
                    this.type = CardType.ATTACK;
                }
            }
        }
        super.update();
    }


    @Override
    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("Odin"));
                addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new IntangiblePlayerPower(abstractPlayer, 1), 1));
                AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
                c.freeToPlayOnce = true;
                addToBot(new MakeTempCardInHandAction(c));
                break;
            case 1:
                if (Shadowverse.Accelerate(this) && this.type == CardType.SKILL){
                    addToBot(new SFXAction("Odin2_Acc"));
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new IntangiblePlayerPower(abstractPlayer, 1), 1));
                    int costToReduce = 0;
                    for (AbstractCard card : abstractPlayer.hand.group){
                        if (card.type == CardType.ATTACK){
                            addToBot(new ExhaustSpecificCardAction(card,abstractPlayer.hand));
                            costToReduce ++;
                        }
                    }
                    AbstractCard o = new Odin2_Copy();
                    o.setCostForTurn(4-costToReduce);
                    addToBot(new MakeTempCardInHandAction(o));
                }else {
                    addToBot(new SFXAction("Odin2"));
                    addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new IntangiblePlayerPower(abstractPlayer, 1), 1));
                }
                break;
        }
    }

    public void triggerOnGlowCheck() {
        if (Shadowverse.Accelerate(this) && this.type == CardType.SKILL) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }


    public AbstractCard makeCopy() {
        return new Odin();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Odin.this.timesUpgraded;
                Odin.this.upgraded = true;
                Odin.this.name = NAME + "+";
                Odin.this.initializeTitle();
                Odin.this.baseDamage = 24;
                Odin.this.upgradedDamage = true;
                Odin.this.previewBranch = 0;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Odin.this.timesUpgraded;
                Odin.this.upgraded = true;
                Odin.this.textureImg = IMG_PATH2;
                Odin.this.loadCardImage(IMG_PATH2);
                Odin.this.name = cardStrings2.NAME;
                Odin.this.initializeTitle();
                Odin.this.rawDescription = cardStrings2.DESCRIPTION;
                Odin.this.initializeDescription();
                Odin.this.previewBranch = 1;
                Odin.this.upgradeBaseCost(4);
                Odin.this.cardsToPreview = new Odin2_Copy();
            }
        });
        return list;
    }
}
