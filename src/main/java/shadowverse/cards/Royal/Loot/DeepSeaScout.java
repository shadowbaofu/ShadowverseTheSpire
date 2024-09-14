package shadowverse.cards.Royal.Loot;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Temp.DreadPirateFlag;
import shadowverse.cards.Neutral.Temp.GildedBoots;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;
import shadowverse.powers.DeepSeaScoutPower;

import java.util.ArrayList;

public class DeepSeaScout extends CustomCard {
    public static final String ID = "shadowverse:DeepSeaScout";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DeepSeaScout");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DeepSeaScout.png";
    private float rotationTimer;
    private int previewIndex;
    private boolean triggered;

    public static ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new GildedBoots());
        list.add(new DreadPirateFlag());
        return list;
    }

    public DeepSeaScout() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(AbstractShadowversePlayer.Enums.CONDEMNED);
        triggered = false;
    }

    @Override
    public void update() {
        super.update();
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
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        if (this.upgraded) {
            addToBot(new MakeTempCardInHandAction(new DreadPirateFlag(), 1));
        } else {
            addToBot(new MakeTempCardInHandAction(new GildedBoots(), 1));
        }
        if (!triggered) {
            addToBot(new ApplyPowerAction(p, p, new DeepSeaScoutPower(p, 1)));
            triggered = true;
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new DeepSeaScout();
    }
}