try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup


with open('README.rst', 'r') as f:
    readme = f.read()
with open('LICENSE.txt', 'r') as f:
    license = f.read()

description = ("Ontotext Self-Service Semantic Suite's text annotation SDK for"
               + " Python3. Easily process your text and documents. For more"
               + " information, visit https://console.s4.ontotext.com/")

setup(
    name="s4sdk",
    version="1.0.17",
    author="Svetlin Slavov",
    author_email="svetlin.slavov@ontotext.com",

    packages=["s4sdk", "s4sdk.models"],
    include_package_data=True,

    url="https://pypi.python.org/pypi/s4sdk",
    license=license,
    description=description,

    long_description=readme,

    install_requires=[
        "requests",
    ],
)
