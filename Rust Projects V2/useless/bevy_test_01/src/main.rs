use bevy::prelude::*;
use bevy::camera::Camera3dBundle;
use bevy::light::DirectionalLightBundle;
use bevy::pbr::{PbrBundle, StandardMaterial};
use bevy::render::mesh::Mesh;
use bevy::prelude::shape;

use bevy::input::keyboard::KeyboardInput;
use bevy::input::Input;               // Input<T>
use bevy::input::keyboard::KeyCode;  // KeyCode enum


fn main() {
    App::new()
        .add_plugins(DefaultPlugins)
        .add_systems(Startup, setup)
        .add_systems(Update, player_movement)
        .run();
}

fn setup(mut commands: Commands) {
    commands.spawn(Camera3dBundle {
        transform: Transform::from_xyz(0.0, 2.0, 5.0)
            .looking_at(Vec3::ZERO, Vec3::Y),
        ..default()
    });

    commands.spawn(DirectionalLightBundle {
        directional_light: DirectionalLight {
            shadows_enabled: true,
            ..default()
        },
        ..default()
    });

    commands.spawn(PbrBundle {
        mesh: Mesh::from(shape::Plane { size: 10.0 }),
        material: StandardMaterial {
            base_color: Color::srgb(0.5, 0.5, 0.5),
            ..default()
        },
        ..default()
    });
}

fn player_movement(
    keyboard_input: Res<Input<KeyCode>>,
    mut query: Query<&mut Transform, With<Camera>>,
    time: Res<Time>,
) {
    let mut transform = query.single_mut().unwrap();
    let speed = 5.0;

    let mut direction = Vec3::ZERO;
    if keyboard_input.pressed(KeyCode::W) { direction.z -= 1.0; }
    if keyboard_input.pressed(KeyCode::S) { direction.z += 1.0; }
    if keyboard_input.pressed(KeyCode::A) { direction.x -= 1.0; }
    if keyboard_input.pressed(KeyCode::D) { direction.x += 1.0; }

    if direction.length_squared() > 0.0 {
        direction = direction.normalize();
        transform.translation += direction * speed * time.delta_secs();
    }
}
