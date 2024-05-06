package bricker.brick_strategies;

import bricker.gameobjects.GameObjectFactory;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the bricks strategy factory.
 * author Tal Yehezkel
 */
public class BrickStrategyFactory {
    private final BrickerGameManager brickerGameManager;
    private final GameObjectFactory gameObjectCollectionFactory;
    private final BasicCollisionStrategy basicCollisionStrategy;
    private int numberOfDoubleBehaviors;

    /**
     * Construct a bricks strategy factory.
     *
     * @param brickerGameManager          The manager of the game.
     * @param gameObjectCollectionFactory The game objects factory.
     */
    public BrickStrategyFactory(BrickerGameManager brickerGameManager,
                                GameObjectFactory gameObjectCollectionFactory) {
        this.brickerGameManager = brickerGameManager;
        this.gameObjectCollectionFactory = gameObjectCollectionFactory;
        this.basicCollisionStrategy = new BasicCollisionStrategy(brickerGameManager);
        this.numberOfDoubleBehaviors = 0;
    }

    /**
     * Return a single strategy from all the options.
     *
     * @return collision strategy.
     */
    public CollisionStrategy getStrategy() {
        numberOfDoubleBehaviors = 0;
        Random random = new Random();
        if (random.nextBoolean()) {
            return basicCollisionStrategy;
        }
        int strategyIndex = new Random().nextInt(Constants.StrategiesConstants.STRATEGIES_NUM);
        return getRandomizedSpecialStrategy(strategyIndex);
    }

    /**
     * Return the collision strategy which represents with that index.
     *
     * @param strategyIndex The index of the strategy to return.
     * @return The collision strategy which represents with that index.
     */
    private CollisionStrategy getRandomizedSpecialStrategy(int strategyIndex) {
        return switch (strategyIndex) {
            case Constants.StrategiesConstants.CAMERA_STRATEGY_INDEX ->
                    new CameraChangeStrategy(brickerGameManager, gameObjectCollectionFactory,
                            basicCollisionStrategy);
            case Constants.StrategiesConstants.DISQUALIFICATION_STRATEGY_INDEX ->
                    new DisqualificationReturnStrategy(brickerGameManager,
                            gameObjectCollectionFactory, basicCollisionStrategy);
            case Constants.StrategiesConstants.DOUBLE_STRATEGY_INDEX -> {
                numberOfDoubleBehaviors += 1;
                yield new DoubleBehaviorStrategy(getStrategiesForDoubleBehavior());
            }
            case Constants.StrategiesConstants.EXTRA_PADDLE_STRATEGY_INDEX ->
                    new ExtraPaddleStrategy(brickerGameManager,
                            gameObjectCollectionFactory, basicCollisionStrategy);
            case Constants.StrategiesConstants.MORE_BALL_STRATEGY_INDEX ->
                    new MoreBallsStrategy(brickerGameManager, gameObjectCollectionFactory,
                            basicCollisionStrategy);
            default -> null;
        };
    }

    /**
     * @return The strategies for the double behavior strategy.
     */
    private List<CollisionStrategy> getStrategiesForDoubleBehavior() {
        List<CollisionStrategy> strategies = new ArrayList<>();
        int allowedDoubleBehaviorNum =
                Constants.StrategiesConstants.MAX_STRATEGIES_FOR_DOUBLE_BEHAVIOR -
                        Constants.StrategiesConstants.DOUBLE_DIFF;
        for (int i = 0; i < Constants.StrategiesConstants.NUM_OF_SPECIAL_STRATEGIES; i++) {
            addSingleSpecialStrategy(allowedDoubleBehaviorNum, strategies);
        }
        return strategies;
    }

    /**
     * Add strategy to the double behavior strategy.
     *
     * @param allowedDoubleBehaviorNum The number of double srategies allowed.
     * @param strategies               The strategies list.
     */
    private void addSingleSpecialStrategy(int allowedDoubleBehaviorNum,
                                          List<CollisionStrategy> strategies) {
        int strategyIndex = new Random().nextInt(Constants.StrategiesConstants.STRATEGIES_NUM);
        if (strategyIndex == Constants.StrategiesConstants.DOUBLE_STRATEGY_INDEX) {
            if (numberOfDoubleBehaviors < allowedDoubleBehaviorNum) {
                CollisionStrategy strategy = getRandomizedSpecialStrategy(strategyIndex);
                strategies.add(strategy);
            } else {
                addSingleSpecialStrategy(allowedDoubleBehaviorNum, strategies);
            }
        } else {
            CollisionStrategy strategy = getRandomizedSpecialStrategy(strategyIndex);
            strategies.add(strategy);
        }
    }
}
